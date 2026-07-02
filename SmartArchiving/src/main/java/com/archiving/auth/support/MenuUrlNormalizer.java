package com.archiving.auth.support;

/**
 * DB 메뉴 URL·요청 URI를 동일 규칙으로 정규화 (MenuController·권한 검사 공용).
 */
public final class MenuUrlNormalizer {

    private MenuUrlNormalizer() {
    }

    /**
     * 요청 URI를 컨텍스트 제외 경로 키로 변환 (예: {@code /transactionIlog}).
     */
    public static String normalizeRequestPath(String contextPath, String requestUri) {
        if (requestUri == null || requestUri.isBlank()) {
            return "/";
        }
        String path = requestUri;
        int q = path.indexOf('?');
        if (q >= 0) {
            path = path.substring(0, q);
        }
        if (contextPath != null && !contextPath.isBlank() && path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        return normalizePathOnly(path);
    }

    /** 클라이언트(메뉴 링크)용 URL — 컨텍스트 경로 포함. */
    public static String toClientMenuUrl(String contextPath, String raw) {
        if (raw == null) {
            return null;
        }
        String u = raw.trim();
        if (u.isEmpty() || "#".equals(u)) {
            return u;
        }
        String lower = u.toLowerCase();
        if (lower.startsWith("http://") || lower.startsWith("https://")) {
            return u;
        }
        String path = normalizeMenuUrlPath(contextPath, u);
        if (path == null) {
            return u;
        }
        String ctx = contextPath == null ? "" : contextPath;
        return ctx + path;
    }

    /**
     * DB에 저장된 메뉴 URL을 경로 키로 변환 (컨텍스트·쿼리 제거).
     */
    public static String normalizeMenuUrlPath(String contextPath, String raw) {
        if (raw == null) {
            return null;
        }
        String u = raw.trim();
        if (u.isEmpty() || "#".equals(u)) {
            return null;
        }
        String lower = u.toLowerCase();
        if (lower.startsWith("http://") || lower.startsWith("https://")) {
            return null;
        }

        String pathPart = u;
        String queryPart = null;
        int qi = u.indexOf('?');
        if (qi >= 0) {
            pathPart = u.substring(0, qi);
            queryPart = u.substring(qi + 1);
        }

        if (contextPath != null && !contextPath.isBlank() && pathPart.startsWith(contextPath + "/")) {
            pathPart = pathPart.substring(contextPath.length());
        } else if (contextPath != null && !contextPath.isBlank() && pathPart.equals(contextPath)) {
            pathPart = "/";
        }
        String normalizedPath = normalizePathOnly(pathPart);
        if (queryPart != null && !queryPart.isEmpty()) {
            return normalizedPath + "?" + queryPart;
        }
        return normalizedPath;
    }

    /**
     * 레거시 JSP·flat·logical view 경로를 하나의 비교 키로 통일.
     */
    public static String normalizePathOnly(String raw) {
        if (raw == null || raw.isBlank()) {
            return "/";
        }
        String path = raw.trim();
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        String pathLower = path.toLowerCase();

        if (!pathLower.startsWith("/views/") && pathLower.contains("/") && pathLower.endsWith(".jsp")) {
            path = "/" + path.substring(path.lastIndexOf('/') + 1);
            pathLower = path.toLowerCase();
        }
        if (pathLower.endsWith(".jsp")) {
            path = path.substring(0, path.length() - 4);
        }
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path.isEmpty() ? "/" : path;
    }

    /**
     * 요청 경로가 허용 메뉴 경로와 일치하는지 (basename·접두/접미 포함).
     */
    public static boolean pathMatches(String requestPath, String allowedPath) {
        if (requestPath == null || allowedPath == null) {
            return false;
        }
        String req = normalizePathOnly(requestPath);
        String allow = normalizePathOnly(allowedPath);
        if (req.equals(allow)) {
            return true;
        }
        if (req.endsWith(allow) || allow.endsWith(req)) {
            return true;
        }
        String reqBase = baseName(req);
        String allowBase = baseName(allow);
        return !reqBase.isEmpty() && reqBase.equals(allowBase);
    }

    private static String baseName(String path) {
        if (path == null || path.isEmpty() || "/".equals(path)) {
            return "";
        }
        String p = path;
        if (p.endsWith("/")) {
            p = p.substring(0, p.length() - 1);
        }
        int i = p.lastIndexOf('/');
        return i >= 0 ? p.substring(i + 1) : p;
    }
}
