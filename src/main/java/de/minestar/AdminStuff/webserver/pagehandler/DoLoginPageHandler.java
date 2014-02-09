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

import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import de.minestar.AdminStuff.webserver.exceptions.LoginInvalidException;
import de.minestar.AdminStuff.webserver.units.AuthHandler;
import de.minestar.AdminStuff.webserver.units.UserData;

public class DoLoginPageHandler extends AbstractHTMLHandler {

	public DoLoginPageHandler(String templateFile) {
		super(false, templateFile);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String handle(HttpExchange http) throws LoginInvalidException {
		Map<String, String> params = (Map<String, String>) http
				.getAttribute("parameters");

		String userName = params.get("txt_username");
		String clearPassword = params.get("txt_password");

		if (userName != null && clearPassword != null
				&& AuthHandler.doLogin(userName, clearPassword)) {
			UserData user = AuthHandler.getUser(userName);
			String response = this.templateString;
			response = response.replaceAll("\\{USERNAME\\}", userName);
			response = response.replaceAll("\\{TOKEN\\}", user.getToken());
			return response;
		} else {
			throw new LoginInvalidException();
		}
	}
}
