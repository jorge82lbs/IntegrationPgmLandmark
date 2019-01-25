/**
* Project: Integration Paradigm-eVeTV
*
* File: SecurityManagerWs.java
*
* Created on:  Enero 23, 2019 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package mx.com.televisa.landamark.view.secman;

import javax.xml.ws.soap.SOAPFaultException;

import mx.com.televisa.landamark.client.auth.SecmanBsAutenticar;
import mx.com.televisa.landamark.client.auth.SecmanBsAutenticar_Service;
import mx.com.televisa.landamark.client.auth.types.ProcessResponse;
import mx.com.televisa.landamark.client.auth.types.UserLogin;
import mx.com.televisa.landamark.view.util.UtilFaces;

/** Clase que hace uso de los servicios de Security Manager <br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Enero 23, 2019, 12:00 pm
 */
public class SecurityManagerWs {
    public SecurityManagerWs() {
        super();
    }
    
    /**
     * Valida en Security Manager si el usuario y contraseña son permitidos en la
     * Aplicacion
     * @autor Jorge Luis Bautista Santiago
     * @param tsUser
     * @param tsPassword
     * @param tsApplicationName
     * @return String
     */
    public String loginSecurityManager(String tsUser, 
                                       String tsPassword, 
                                       String tsApplicationName
                                      ) throws Exception {
        String    lsToken = null;
        String    lsUser = "";
        String    lsPassword = "";
        UtilFaces loUf = new UtilFaces();
        try {
            String lsKey= loUf.getKeyDecoder();
            lsUser = loUf.encryptObject(tsUser,lsKey);
            lsPassword = loUf.encryptObject(tsPassword, lsKey);
        } catch (Exception loEx) {
            lsToken = null;
        }
        SecmanBsAutenticar_Service loSecmanBsAuthService = 
            new SecmanBsAutenticar_Service();
    
        try{
          
            SecmanBsAutenticar         loSecmanBsAuth = 
                loSecmanBsAuthService.getSecmanBsAutenticarSoap12HttpPort();
            UserLogin                  loUserLogin = new UserLogin();
            loUserLogin.setUserName(lsUser);
            loUserLogin.setUserPassword(lsPassword);
            loUserLogin.setNomAplicacion(tsApplicationName);
            loUserLogin.setAccion("login");          
            loUserLogin.setToken("");
            ProcessResponse            loProcessResponse;
            loProcessResponse = loSecmanBsAuth.autenticarUsuario(loUserLogin);
            if (loProcessResponse.getResult().startsWith("FAILED")){                
                throw new Exception (loProcessResponse.getError());
            }
            else{
                lsToken = loProcessResponse.getResult();                
            }    
        }catch(SOAPFaultException loEx){
            ;
        } catch (Exception loEx) {
            throw new Exception (loEx.getMessage());
        }                        
        return lsToken;
    }
    
    /**
     * Valida en Security Manager si el usuario y contraseÃ±a son permitidos en la
     * Aplicacion
     * @autor Jorge Luis Bautista Santiago
     * @param tsUser
     * @param psPassword
     * @param tsApplicationName
     * @return void
     */
    public void logoutSecurityManager(String tsUser, 
                                      String tsApplicationName
                                      ) throws Exception {    
        String    lsUser = "";
        UtilFaces loUf = new UtilFaces();
        try {
            String lsKey= loUf.getKeyDecoder();
            lsUser = loUf.encryptObject(tsUser,lsKey);
        } catch (Exception loEx) {
            ;
        }

        SecmanBsAutenticar_Service loSecmanBsAuthService = 
            new SecmanBsAutenticar_Service();
        try{
            SecmanBsAutenticar     loSecmanBsAuth = 
                loSecmanBsAuthService.getSecmanBsAutenticarSoap12HttpPort();
            UserLogin              loUserLogin = new UserLogin();
            loUserLogin.setUserName(lsUser);
            loUserLogin.setNomAplicacion(tsApplicationName);
            loUserLogin.setAccion("logout");          
            loUserLogin.setToken("");
            ProcessResponse        loProcessResponse;
            loProcessResponse = loSecmanBsAuth.autenticarUsuario(loUserLogin);
            if (loProcessResponse.getResult().startsWith("FAILED")){                
                throw new Exception (loProcessResponse.getError());
            }   
        }catch(SOAPFaultException loEx){
            ;
        } catch (Exception loEx) {
            throw new Exception (loEx.getMessage());
        }                        
    }
    
}
