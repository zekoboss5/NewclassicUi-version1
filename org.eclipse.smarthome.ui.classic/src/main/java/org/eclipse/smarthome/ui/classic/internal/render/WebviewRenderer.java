/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.ui.classic.internal.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.smarthome.model.sitemap.Webview;
import org.eclipse.smarthome.model.sitemap.Widget;
import org.eclipse.smarthome.ui.classic.render.RenderException;
import org.eclipse.smarthome.ui.classic.render.WidgetRenderer;
import org.eclipse.smarthome.ui.classic.rest.render.RestRenderer;
import org.eclipse.smarthome.ui.classic.rest.render.UrlBuilder;

/**
 * This is an implementation of the {@link WidgetRenderer} interface, which
 * can produce HTML code for Webview widgets.
 *
 * @author Kai Kreuzer - Initial contribution and API
 *
 */
public class WebviewRenderer extends AbstractWidgetRenderer {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRender(Widget w) {

        return w instanceof Webview;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EList<Widget> renderWidget(Widget w, StringBuilder sb) throws RenderException {

        Webview webview = (Webview) w;
        String snippet = getSnippet("webview");

        System.err.println("snippet de depart " + snippet);
        int height = webview.getHeight();
        if (height == 0) {
            height = 1;
        }

        snippet = StringUtils.replace(snippet, "%url%", webview.getUrl());
        snippet = StringUtils.replace(snippet, "%height%", Integer.toString(height * 36));
        sb.append(snippet);

        try {

            ArrayList<HashMap<String, String>> list;
            list = RestRenderer.parse();

            Iterator<HashMap<String, String>> it = list.iterator();

            while (it.hasNext()) {
                HashMap<String, String> temp = it.next();
                UrlBuilder u = new UrlBuilder(temp.get("ip"), temp.get("name"));

                u.BuildUrls();
                if (u.isRegistered()) {
                    System.out.println("image_URL: " + u.getImageUrl() + "\n stream_url: " + u.getStreamUrl()
                            + "\n pan_url: " + u.getPanUrl() + "\n tilt_url: " + u.getTiltUrl());
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
