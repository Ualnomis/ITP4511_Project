/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;

/**
 *
 * @author 2689
 */
public class HeaderTag extends SimpleTagSupport {
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            out.println("<div class=\"mb-4\">\n" +
"                            <img src=\"assets/img/logo_vtc.svg\" alt=\"bootraper logo\" class=\"app-logo\" style=\"width: 210px; height: 42px;\">\n" +
"                        </div>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
