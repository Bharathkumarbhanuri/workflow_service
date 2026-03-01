package com.nerchuko.workflow_service_backend.emails;

public final class EmailTemplates {

    private EmailTemplates() {}

    // Basic HTML escape (enough for email body text)
    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // Converts plain text -> HTML paragraphs with <br/>
    private static String textToHtml(String text) {
        String safe = escapeHtml(text);
        // keep line breaks entered in textarea
        return safe.replace("\r\n", "\n").replace("\n", "<br/>");
    }

    // If user already typed real HTML, don’t escape it — just insert as-is.
    private static boolean looksLikeHtml(String s) {
        if (s == null) return false;
        String t = s.toLowerCase();
        return t.contains("<div") || t.contains("<p") || t.contains("<br") || t.contains("<table")
                || t.contains("<html") || t.contains("<body") || t.contains("<h1") || t.contains("<h2");
    }

    public static String wrapBranded(String subject, String body) {
        String content = looksLikeHtml(body) ? body : textToHtml(body);

        String safeSubject = escapeHtml(subject);

        // Table-based layout for best email-client compatibility
        return """
            <!doctype html>
            <html lang="en">
              <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <meta name="x-apple-disable-message-reformatting" />
                <title>%s</title>
              </head>
              <body style="margin:0;padding:0;background:#f6f8fb;font-family:Arial,Helvetica,sans-serif;color:#111827;">
                <table role="presentation" cellpadding="0" cellspacing="0" border="0" width="100%%" style="background:#f6f8fb;padding:24px 0;">
                  <tr>
                    <td align="center">

                      <!-- Container -->
                      <table role="presentation" cellpadding="0" cellspacing="0" border="0" width="640" style="width:640px;max-width:94%%;">
                        <tr>
                          <td style="padding:0 12px;">

                            <!-- Banner -->
                            <table role="presentation" cellpadding="0" cellspacing="0" border="0" width="100%%"
                                   style="border-radius:14px 14px 0 0;overflow:hidden;background:linear-gradient(90deg,#2563eb,#7c3aed);">
                              <tr>
                                <td style="padding:18px 20px;">
                                  <div style="font-size:14px;opacity:.95;color:#ffffff;letter-spacing:.2px;">
                                    Workflow Services
                                  </div>
                                  <div style="font-size:20px;font-weight:700;color:#ffffff;margin-top:6px;line-height:1.2;">
                                    %s
                                  </div>
                                </td>
                              </tr>
                            </table>

                            <!-- Body card -->
                            <table role="presentation" cellpadding="0" cellspacing="0" border="0" width="100%%"
                                   style="background:#ffffff;border:1px solid #e5e7eb;border-top:none;border-radius:0 0 14px 14px;">
                              <tr>
                                <td style="padding:20px 20px 10px 20px;">
                                  <div style="font-size:14px;line-height:1.6;color:#111827;">
                                    %s
                                  </div>
                                </td>
                              </tr>

                              <tr>
                                <td style="padding:12px 20px 18px 20px;">
                                  <div style="border-top:1px solid #eef2f7;padding-top:12px;font-size:12px;color:#6b7280;line-height:1.5;">
                                    You’re receiving this email because a workflow step triggered an email action.
                                    <br/>
                                    <span style="color:#9ca3af;">© Workflow Services</span>
                                  </div>
                                </td>
                              </tr>
                            </table>

                          </td>
                        </tr>
                      </table>
                      <!-- /Container -->

                    </td>
                  </tr>
                </table>
              </body>
            </html>
            """.formatted(safeSubject, safeSubject, content);
    }
}