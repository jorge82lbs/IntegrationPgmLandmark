/**
* Project: Paradigm - Landmark 
*
* File: LoginBean.java
*
* Created on:  Enero 09, 2019 at 11:00
*
* Copyright (c) - Omw - 2019
*/

package mx.com.televisa.landamark.view.beans;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.rich.input.RichInputText;

/** Esta clase es un bean que enlaza la pantalla de Login<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Enero 09, 2019, 12:00 pm
 */
public class LoginBean {
    private RichInputText poUserName;
    private RichInputText poPassword;

    public LoginBean() {
    }

    
    /**
     * Metodo que dispara el boton de ingresar, valida el usuario y psPassword
     * para la aplicacion de Integracion
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String loginAction() {
        FacesContext        loContext = FacesContext.getCurrentInstance();
        ExternalContext     loEctx = loContext.getExternalContext();        
        String              lsUrl = 
            loEctx.getRequestContextPath() + "/faces/homePage";
        try {
            loEctx.redirect(lsUrl);
        } catch (IOException loEx) {
            System.out.println("Error al redireccionar a home");
        }
        return null;
    }

    public String clearAction() {
        getPoUserName().setValue(null);
        getPoPassword().setValue(null);
        return null;
    }
    
    /****************** SETTERS AND GETTERS ******************************/    
    
    public void setPoUserName(RichInputText poUserName) {
        this.poUserName = poUserName;
    }

    public RichInputText getPoUserName() {
        return poUserName;
    }

    public void setPoPassword(RichInputText poPassword) {
        this.poPassword = poPassword;
    }

    public RichInputText getPoPassword() {
        return poPassword;
    }

}
