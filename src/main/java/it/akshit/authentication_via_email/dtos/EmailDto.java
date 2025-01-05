package it.akshit.authentication_via_email.dtos;

import lombok.Data;

@Data
public class EmailDto {
    private String to;
    private String subject;
    private String body;

    EmailDto(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public static EmailDtoBuilder builder() {
        return new EmailDtoBuilder();
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static class EmailDtoBuilder {
        private String to;
        private String subject;
        private String body;

        EmailDtoBuilder() {
        }

        public EmailDtoBuilder to(String to) {
            this.to = to;
            return this;
        }

        public EmailDtoBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailDtoBuilder body(String body) {
            this.body = body;
            return this;
        }

        public EmailDto build() {
            return new EmailDto(this.to, this.subject, this.body);
        }

        public String toString() {
            return "EmailDto.EmailDtoBuilder(to=" + this.to + ", subject=" + this.subject + ", body=" + this.body + ")";
        }
    }
}
