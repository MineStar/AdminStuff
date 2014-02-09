/*
 * Copyright (C) 2014 MineStar.de 
 * 
 * This file is part of AdminStuff.
 * 
 * AdminStuff is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * AdminStuff is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with AdminStuff.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.AdminStuff.webserver.pagehandler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import com.sun.net.httpserver.HttpExchange;

import de.minestar.AdminStuff.webserver.exceptions.LoginInvalidException;

public abstract class AbstractHTMLHandler {

	protected String templateString = "";
	private final boolean needsLogin;

	protected AbstractHTMLHandler(boolean needsLogin, String path) {
		this.needsLogin = needsLogin;
		this.templateString = this.loadTemplate(path);
	}

	public boolean needsLogin() {
		return this.needsLogin;
	}

	private final String loadTemplate(String templateFile) {
		if (templateFile.startsWith("/")) {
			templateFile = templateFile.replaceFirst("/", "");
		}
		File file = new File("templates/" + templateFile);

		if (!file.exists()) {
			return "<b>TEMPLATE NOT FOUND!</b>";
		}
		try {
			return com.google.common.io.Files.toString(file,
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			return "<b>TEMPLATE NOT LOADED!</b><br/><br/>Error:<br/>"
					+ e.getMessage();
		}
	}

	public abstract String handle(HttpExchange http) throws IOException,
			LoginInvalidException;
}
