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

package uk.ac.ebi.biostudies.submissiontool.rest.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.biostudies.submissiontool.SessionAttributes;
import uk.ac.ebi.biostudies.submissiontool.rest.data.UserSession;

import javax.annotation.Priority;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Olga Melnichuk
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

            UserSession session = getUserSession();

            if (!isUserAllowed(session, rolesSet)) {
                requestContext.abortWith(accessDenied());
            }
        }
    }

    private Response accessDenied() {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
/*
    private Response accessForbidden() {
        return Response.status(Response.Status.FORBIDDEN).build();
    }
*/

    private UserSession getUserSession() {
        String sessid = getSessionId();
        logger.debug("sessionId=" + sessid);
        if (sessid == null) {
            SessionAttributes.setUserSession(request, null);
            return null;
        }

        UserSession session = new UserSession(sessid);
        SessionAttributes.setUserSession(request, session);
        return session;
    }

    private String getSessionId() {
        return request.getHeader("X-Session-Token");
    }

    private boolean isUserAllowed(final UserSession userSession, final Set<String> rolesSet) {
        return userSession != null;
    }

}
