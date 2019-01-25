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

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.televisa.landamark.model.AppModuleImpl;
import mx.com.televisa.landamark.view.secman.SecurityManagerWs;

import mx.com.televisa.landamark.view.util.UtilFaces;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

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
    private RichPopup poPopupExitConfirm;

    public LoginBean() {
    }

    
    /**
     * Metodo que dispara el boton de ingresar, valida el usuario y psPassword
     * para la aplicacion de Integracion
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String loginAction() {
        String  lsUserName = 
            getPoUserName().getValue() == null ? null : 
            getPoUserName().getValue().toString();
        String  lsPassword = 
            getPoPassword().getValue() == null ? null : 
            getPoPassword().getValue().toString();      
        boolean lbFlagError = false;
        String  lsErrorMessage = null;
        String  lsTokenSecman;
        if (lsUserName != null && lsPassword != null) {
            FacesContext        loContext = FacesContext.getCurrentInstance();
            ExternalContext     loEctx = loContext.getExternalContext();
            String              lsUrl = 
                loEctx.getRequestContextPath() + "/faces/homePage";
            try {                
                lsTokenSecman = 
                    validateSecmanUser(lsUserName, lsPassword); 
                    //lsTokenSecman = "123456789"; //TEMPORAL
                if (lsTokenSecman != null) {
                    System.out.println("Redireccionando correctamente........");
                    loEctx.redirect(lsUrl);
                }
            } catch (IOException loEx) {
                System.out.println("Error al redireccionar a home");
                lbFlagError = true;
                lsErrorMessage = loEx.getMessage();
            } catch (Exception loExp) {
                lbFlagError = true;
                lsErrorMessage = loExp.getMessage();
            }
        }
        else{
            lbFlagError = true;
            lsErrorMessage = "Los Campos Son Requeridos";
        }
        if(lbFlagError){
            FacesMessage loMsg = 
                new FacesMessage(lsErrorMessage);
            loMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        }
        
        
        return null;
    }

    public String clearAction() {
        getPoUserName().setValue(null);
        getPoPassword().setValue(null);
        return null;
    }
    
    /**
     * Valida usuario y password en Security Manager y verifica <br>
     * si el usuario tiene permiso de acceder con la aplicacion deseada
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String validateSecmanUser(String tsUserName, 
                                     String tsPassword
                                    ) throws Exception {
        String            lsResponse = null;        
        SecurityManagerWs loSecMan = new SecurityManagerWs();
        try {
            System.out.println("Validando temporalmente en Integracion");
            lsResponse = 
                loSecMan.loginSecurityManager(tsUserName,
                                              tsPassword, 
                                              "IntegrationEveTv");

        } catch (Exception loEx) {
            throw new Exception(loEx.getMessage());
        }
        System.out.println("Token: "+lsResponse);
        return lsResponse;
    }
        
    /**
     * Sale de la aplicacion y elimina la session
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String exitAppIntegraion() {
        String              lsAmDef =
            "com.televisa.landamark.model.AppModuleImpl";
        String              lsConfig = "AppModuleLocal";
        ExternalContext     loEctx = 
            FacesContext.getCurrentInstance().getExternalContext();
        String              lsUrl = loEctx.getRequestContextPath() + "/faces/indexPage";
        HttpSession         loSession = (HttpSession) loEctx.getSession(false);
        HttpServletResponse loResponse = (HttpServletResponse) loEctx.getResponse();
        SecurityManagerWs   loSecMan = new SecurityManagerWs();
        try {
            System.out.println("Conectando con base de datos");
            ApplicationModule   loAm =
                Configuration.createRootApplicationModule(lsAmDef, lsConfig);
            AppModuleImpl loService = (AppModuleImpl)loAm;
            System.out.println("Salir de Secman");
            try {
                String lsUserName = "jlbautistas";
                    //loService.getValueSessionFromAttribute("loggedIntegrationUser") == null ? null :
                    //loService.getValueSessionFromAttribute("loggedIntegrationUser").toString();                
                loSecMan.logoutSecurityManager(lsUserName, "IntegrationEveTv");
                System.out.println("Saliendo de Secman correctamente");
            } catch (Exception loEx) {
                System.out.println("Util-ERROR: "+loEx.getMessage());
            } finally {
                System.out.println("liberando conexión con base de datos");
                Configuration.releaseRootApplicationModule(loAm, true);
                loAm.remove();
            }
        } catch (Exception loEx) {
            System.out.println("No es posible conectar con la base de datos "+loEx.getMessage());
        }
        loSession.invalidate();
        try {
            loResponse.sendRedirect(lsUrl);
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch (Exception loEx) {
            System.out.println("Error al salir "+loEx.getMessage());
            loEx.printStackTrace();
        }
        return null;
    }

    /**
     * Cancela accion salir de la aplicacion
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String cancelExitAppIntegraion() {
        new UtilFaces().hidePopup(getPoPopupExitConfirm());
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

    public void setPoPopupExitConfirm(RichPopup poPopupExitConfirm) {
        this.poPopupExitConfirm = poPopupExitConfirm;
    }

    public RichPopup getPoPopupExitConfirm() {
        return poPopupExitConfirm;
    }
}
