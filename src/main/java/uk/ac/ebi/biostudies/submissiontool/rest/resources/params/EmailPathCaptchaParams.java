/*
 * Copyright (c) 2017 European Molecular Biology Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or impl
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.ebi.biostudies.submissiontool.rest.resources.params;

/**
 * @author olkin
 */
public class EmailPathCaptchaParams {
    private String email;
    private String captcha;
    private String path;

    public String getEmail() {
        return email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public String getPath() {
        return path;
    }

    public EmailPathCaptchaParams withPath(String newPath) {
        EmailPathCaptchaParams p = new EmailPathCaptchaParams();
        p.email = email;
        p.captcha = captcha;
        p.path = newPath;
        return p;
    }

    @Override
    public String toString() {
        return "EmailPathCaptchaParams{" +
                "email='" + email + '\'' +
                ", captcha='" + captcha + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
