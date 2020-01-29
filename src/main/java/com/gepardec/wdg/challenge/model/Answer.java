package com.gepardec.wdg.challenge.model;

import com.gepardec.wdg.challenge.validation.AnswerValid;
import com.gepardec.wdg.application.validation.Base64;
import com.gepardec.wdg.application.validation.Base64Length;
import com.gepardec.wdg.client.personio.Source;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AnswerValid
public class Answer {

    @NotNull(message = "{AnswerModel.jobId.notNull}")
    private Integer jobId;

    @NotNull(message = "{AnswerModel.challengeAnswer.notNull}")
    private String answer;

    @NotNull(message = "{AnswerModel.firstName.notNull}")
    private String firstName;

    @NotNull(message = "{AnswerModel.lastName.notNull}")
    private String lastName;

    @NotNull(message = "{AnswerModel.email.notNull}")
    @Email(message = "{AnswerModel.email.email}")
    private String email;

    @Base64(message = "{AnswerModel.cv.base64}")
    @Base64Length(max = 10_485_760, message = "{AnswerModel.cv.base64Length}")
    private String cv;

    @URL(message = "{AnswerModel.xingLink.url}")
    private String xingLink = "";

    @URL(message = "{AnswerModel.linkedInLink.url}")
    private String linkedInLink = "";

    private Source source = Source.SONSTIGES;

    private String otherSource = "Bewerber hat nichst definiert";

    private String title = "";

    private String phone = "";

    private String messageToGepardec = "";

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getXingLink() {
        return xingLink;
    }

    public void setXingLink(String xingLink) {
        this.xingLink = xingLink;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getOtherSource() {
        return otherSource;
    }

    public void setOtherSource(String otherSource) {
        this.otherSource = otherSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessageToGepardec() {
        return messageToGepardec;
    }

    public void setMessageToGepardec(String messageToGepardec) {
        this.messageToGepardec = messageToGepardec;
    }
}
