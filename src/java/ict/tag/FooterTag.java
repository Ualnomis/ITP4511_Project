/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author 2689
 */
public class FooterTag extends SimpleTagSupport {

    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            String output = "<script src=\"assets/vendor/jquery/jquery.min.js\"></script>\n"
                    + "    <script src=\"assets/vendor/bootstrap/js/bootstrap.bundle.min.js\"></script>\n"
                    + "    <script src=\"assets/vendor/datatables/datatables.min.js\"></script>\n"
                    + "    <script src=\"assets/js/initiate-datatables.js\"></script>\n"
                    + "    <script src=\"assets/js/script.js\"></script>";
            out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
