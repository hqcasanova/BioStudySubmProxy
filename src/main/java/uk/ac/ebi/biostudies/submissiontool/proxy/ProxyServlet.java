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

package uk.ac.ebi.biostudies.submissiontool.proxy;

import uk.ac.ebi.biostudies.submissiontool.context.AppConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static uk.ac.ebi.biostudies.submissiontool.context.AppContext.getConfig;

@WebServlet("/raw/*")
public class ProxyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Proxy proxy;

    @Override
    public void init() throws ServletException {
        AppConfig config = getConfig(getServletContext());
        proxy =/* config.isOfflineModeOn() ?
                new ProxyStub() :*/
                new RemoteProxy(config.getServerUrl(), source -> source.replace("/raw", ""));
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        proxy.proxyGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proxy.proxyPost(req, resp);
    }
}
