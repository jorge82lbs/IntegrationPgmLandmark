package mx.com.televisa.landamark.view.beans;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import mx.com.televisa.landamark.model.AppModuleImpl;
import mx.com.televisa.landamark.model.types.LmkIntServicesCatRowBean;
import mx.com.televisa.landamark.model.types.LmkIntServicesParamsRowBean;
import mx.com.televisa.landamark.view.model.daos.ViewObjectDao;

import mx.com.televisa.landamark.view.types.SelectOneItemBean;

import mx.com.televisa.landamark.view.util.UtilFaces;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierNodeBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

public class ProcessBean {
    private RichSelectOneChoice poFilterDesServiceSel;
    private RichSelectOneChoice poFilterServiceSel;
    private RichInputText poFilterService;
    private RichInputText poFilterWsdl;
    private RichSelectOneChoice poFilterSystem;
    private RichSelectOneChoice poFilterSystemOr;
    private RichSelectOneChoice poFilterSystemDes;
    private RichSelectOneChoice poFilterAsynSel;
    private RichSelectOneChoice poFilterStatusSel;
    private RichTable poTblMain;
    private RichInputText poUpdIdService;
    private RichInputText poUpdNomService;
    private RichInputText poUpdDesService;
    private RichSelectOneChoice poUpdSystem;
    private RichSelectOneChoice poUpdSystemOr;
    private RichPopup poPopupDelete;
    private RichPanelLabelAndMessage poDeleteMsgLbl;
    private RichOutputText poDeleteIdBinding;
    private RichPopup poPopupUpdate;
    private RichSelectOneChoice poUpdSystemDes;
    private RichSelectBooleanCheckbox poUpdAsyn;
    private RichSelectBooleanCheckbox poUpdStatus;
    String                            gsEntityView = "LmkIntServicesCatVwView1";
    String                            gsEntityIterator = "LmkIntServicesCatVwView1Iterator";                                            
    String                            gsAppModule = "AppModuleDataControl";
    String                            gsAmDef = "mx.com.televisa.landamark.model.AppModuleImpl";
    String                            gsConfig = "AppModuleLocal";
    private RichPopup poPopupSave;
    private RichInputText poSaveNomService;
    private RichSelectOneChoice poSaveNomServiceSel;
    private RichInputText poSaveWsdl;
    private RichSelectOneChoice poSaveSystem;
    private RichSelectOneChoice poSaveSystemOr;
    private RichSelectOneChoice poSaveSystemDes;
    private RichSelectBooleanCheckbox poSaveAsyn;
    private RichSelectBooleanCheckbox poSaveStatus;
    private RichInputText poUpdWsdl;
    private RichPopup poPopupMerge;
    private RichInputText poMergeIdService;
    private RichInputText poMergeNomService;
    private RichSelectBooleanCheckbox poMerge2can;
    private RichSelectBooleanCheckbox poMerge5can;
    private RichSelectBooleanCheckbox poMerge9can;
    private RichInputText poMergeCliente;

    public ProcessBean() {
    }
    
    /**
     * Reinicia los valores de busqueda
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return void
     */
    public void resetFilterValues(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        getPoFilterService().setValue("");
        getPoFilterWsdl().setValue("");
        getPoFilterSystem().setValue("");
        getPoFilterSystemOr().setValue("");
        getPoFilterSystemDes().setValue("");
        getPoFilterStatusSel().setValue("");
        getPoFilterAsynSel().setValue("");
    }

    /**
     * Actualiza la tabla principal a su estado inicial
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String refreshMainTable() {
        new UtilFaces().refreshTableWhereIterator("1 = 1 ",
                                                  gsEntityIterator,
                                                  getPoTblMain()
                                                  );
        return null;
    }
    
    /**
     * Ejecuta la busqueda en base a los parametros
     * @autor Jorge Luis Bautista Santiago       
     * @return String
     */
    public String searchFilterAction() {
        String lsQuery = " 1 = 1 ";
        String lsNomServiceSelected = 
            getPoFilterDesServiceSel().getValue() == null ? "" : 
            getPoFilterDesServiceSel().getValue().toString();        
        if(!lsNomServiceSelected.equalsIgnoreCase("")){
            lsQuery += " AND ID_SERVICE = " + lsNomServiceSelected;
        }
        
        String lsNomService = 
            getPoFilterServiceSel().getValue() == null ? "" :
            getPoFilterServiceSel().getValue().toString();        
        if(!lsNomService.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(NOM_SERVICE) LIKE '" + 
                       lsNomService.toUpperCase() + "%'";
        }
        /*
        String lsIndServiceWsdl = 
            getPoFilterWsdl().getValue() == null ? "" :
            getPoFilterWsdl().getValue().toString();
        if(!lsIndServiceWsdl.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(IND_SERVICE_WSDL) LIKE '" +
                       lsIndServiceWsdl.toUpperCase() + "%'";
        }
                
        String lsIndSystem = 
            getPoFilterSystem().getValue() == null ? "" : 
            getPoFilterSystem().getValue().toString();
        if(!lsIndSystem.equalsIgnoreCase("")){
            lsQuery += " AND IND_SYSTEM = '" + lsIndSystem + "'";
        }
        String lsIndOrigin = 
            getPoFilterSystemOr().getValue() == null ? "" : 
            getPoFilterSystemOr().getValue().toString();
        if(!lsIndOrigin.equalsIgnoreCase("")){
            lsQuery += " AND IND_ORIGIN = '" + lsIndOrigin + "'";
        }
        
        String lsIndDestiny = 
            getPoFilterSystemDes().getValue() == null ? "" :
            getPoFilterSystemDes().getValue().toString();
        if(!lsIndDestiny.equalsIgnoreCase("")){
            lsQuery += " AND IND_DESTINY = '" + lsIndDestiny + "'";
        }
        
        String lsIndSynchronous = 
            getPoFilterAsynSel().getValue() == null ? "" :
            getPoFilterAsynSel().getValue().toString();
        if(!lsIndSynchronous.equalsIgnoreCase("")){
            lsQuery += " AND IND_SYNCHRONOUS = '" + lsIndSynchronous + "'";
        }                      

        */
        String lsIndEstatus = 
            getPoFilterStatusSel().getValue() == null ? "" :
            getPoFilterStatusSel().getValue().toString();        
        if(!lsIndEstatus.equalsIgnoreCase("")){
            lsQuery += " AND IND_ESTATUS = '" + lsIndEstatus + "'";
        }
                
        new UtilFaces().refreshTableWhereIterator(lsQuery,
                                                  gsEntityIterator,
                                                  getPoTblMain()
                                                  );
        return null;
    }


    public void showSavePopup(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        getPoSaveNomService().setValue("");
        getPoSaveWsdl().setValue("");
        getPoSaveSystem().setValue("");
        getPoSaveSystemOr().setValue("");
        getPoSaveSystemDes().setValue("");
        getPoSaveStatus().setValue("");
        getPoSaveAsyn().setValue("");
        new UtilFaces().showPopup(getPoPopupSave());
    }

    /**
     * Muesta valores para configurar cron por servicio seleccionado
     * @autor Jorge Luis Bautista Santiago
     * @param tsStrSearch
     * @return String
     */
    
    public void showEditPopup(ActionEvent loActionEvent) {  
        loActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblMain().getSelectedRowData();
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();
        String                   lsNomService = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();
        String                   lsDesService = 
            loNode.getAttribute("IndDescService") == null ? "" : 
            loNode.getAttribute("IndDescService").toString();
        String                   lsIndServiceWsdl = 
            loNode.getAttribute("IndServiceWsdl") == null ? "" : 
            loNode.getAttribute("IndServiceWsdl").toString();
        String                   lsIndSystem = 
            loNode.getAttribute("IndSystem") == null ? "" : 
            loNode.getAttribute("IndSystem").toString();
        String                   lsIndOrigin = 
            loNode.getAttribute("IndOrigin") == null ? "" : 
            loNode.getAttribute("IndOrigin").toString();
        String                   lsIndDestiny = 
            loNode.getAttribute("IndDestiny") == null ? "" : 
            loNode.getAttribute("IndDestiny").toString();
        String                   lsIndSynchronous = 
            loNode.getAttribute("IndSynchronous") == null ? "" : 
            loNode.getAttribute("IndSynchronous").toString();
        String                   lsIndEstatus = 
            loNode.getAttribute("IndEstatus") == null ? "" : 
            loNode.getAttribute("IndEstatus").toString();

        getPoUpdIdService().setValue(lsIdService);        
        getPoUpdNomService().setValue(lsNomService);
        getPoUpdDesService().setValue(lsDesService);
        getPoUpdWsdl().setValue(lsIndServiceWsdl);
        getPoUpdSystem().setValue(lsIndSystem);
        getPoUpdSystemOr().setValue(lsIndOrigin);
        getPoUpdSystemDes().setValue(lsIndDestiny);
                
        if(lsIndSynchronous.equalsIgnoreCase("1")){
            getPoUpdAsyn().setSelected(true);
        }else{
            getPoUpdAsyn().setSelected(false);
        }
        if(lsIndEstatus.equalsIgnoreCase("1")){
            getPoUpdStatus().setSelected(true);
        }else{
            getPoUpdStatus().setSelected(false);
        }
        
        new UtilFaces().showPopup(getPoPopupUpdate());
    }

    /**
     * Muestra popup que solicita datos para eliminar el servicio
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return void
     */
    public void showDeletePopup(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblMain().getSelectedRowData();
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();
        String                   lsServiceName = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();
        String                   lsServiceDesc = 
            loNode.getAttribute("IndDescService") == null ? "" : 
            loNode.getAttribute("IndDescService").toString();
        getPoDeleteIdBinding().setValue(lsIdService);
        getPoDeleteMsgLbl().setLabel("Eliminar a " + lsServiceDesc + " - " + lsServiceName + "?");
        new UtilFaces().showPopup(getPoPopupDelete());
    }

    /**
     * Muestra panel de tab para edita
     * @autor Jorge Luis Bautista Santiago  
     * @param actionEvent, 
     * @return void
     */
    public void showTabCronService(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        // Add event code here...
        System.out.println("Por  el momento deshabilitado");
    }


    public String saveAction() {
        Integer liIdService = new ViewObjectDao().getMaxIdServicesCatalog() + 1;
        String lsDescService = 
            getPoSaveNomService().getValue() == null ? "" : 
            getPoSaveNomService().getValue().toString();
        String lsNomService = 
            getPoSaveNomServiceSel().getValue() == null ? "" : 
            getPoSaveNomServiceSel().getValue().toString();
        String lsIndServiceWsdl = 
            getPoSaveWsdl().getValue() == null ? "" :
            getPoSaveWsdl().getValue().toString();
        String lsIndSystem = 
            getPoSaveSystem().getValue() == null ? "" : 
            getPoSaveSystem().getValue().toString();
        String lsIndOrigin = 
            getPoSaveSystemOr().getValue() == null ? "" :
            getPoSaveSystemOr().getValue().toString();
        String lsIndDestiny = 
            getPoSaveSystemDes().getValue() == null ? "" : 
            getPoSaveSystemDes().getValue().toString();
        String lsIndEstatus = 
            getPoSaveStatus().getValue() == null ? "" : 
            getPoSaveStatus().getValue().toString();
        String lsIndSynchronous = 
            getPoSaveAsyn().getValue() == null ? "" : 
            getPoSaveAsyn().getValue().toString();
        String lsStatusTab = "0";
        if(lsIndEstatus.equalsIgnoreCase("true")){
            lsStatusTab = "1";
        }
        String lsAsynTab = "0";
        if(lsIndSynchronous.equalsIgnoreCase("true")){
            lsAsynTab = "1";
        }
        ApplicationModule         loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleImpl loService = 
            (AppModuleImpl)loAm;        
        try{
            Integer liRes = 0;
            liRes = loService.validateExistByNomServicesCatModel(lsNomService);
            if(liRes == 0){
                LmkIntServicesCatRowBean loLmkBean = new LmkIntServicesCatRowBean();
                loLmkBean.setLiIdService(liIdService);
                loLmkBean.setLsNomService(lsNomService);
                loLmkBean.setLsIndDescService(lsDescService);
                loLmkBean.setLsIndServiceWsdl(lsIndServiceWsdl);
                loLmkBean.setLsIndSystem(lsIndSystem);
                loLmkBean.setLsIndOrigin(lsIndOrigin);
                loLmkBean.setLsIndDestiny(lsIndDestiny);
                loLmkBean.setLsIndEstatus(lsStatusTab);
                loLmkBean.setLsIndSynchronous(lsAsynTab);
                
                loService.insertServicesCatModel(loLmkBean);  
            }else{
                FacesMessage loMsg;
                loMsg = new FacesMessage("El Servicio Solo Puede Existir Una Vez");
                loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, loMsg);
            }
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                  gsEntityIterator,
                                                  getPoTblMain()
                                                  );
        new UtilFaces().hidePopup(getPoPopupSave());
        return null;
    }

    /**
     * Cancela la accion de guardar y oculta el popup
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String cancelSaveAction() {
        new UtilFaces().hidePopup(getPoPopupSave());
        return null;
    }

    /**
     * Elimina el servicio web de la base de datos
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String deleteAction() {
        String                    lsIdService = 
            getPoDeleteIdBinding().getValue() == null ? "" : 
            getPoDeleteIdBinding().getValue().toString();
        ApplicationModule         loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleImpl loService = (AppModuleImpl)loAm;
        try{
            loService.deleteServicesCatModel(Integer.parseInt(lsIdService));            
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                  gsEntityIterator,
                                                  getPoTblMain()
                                                  );
        new UtilFaces().hidePopup(getPoPopupDelete());
        return null;
    }
   
    /**
     * Oculta popup de eliminar servicio
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String cancelDeleteAction() {
        new UtilFaces().hidePopup(getPoPopupDelete());
        return null;
    }


    /**
     * Actualiza en base de datos el servicio seleccionado
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String updateAction() {        
        String                   lsIdService = 
            getPoUpdIdService().getValue() == null ? "" : 
            getPoUpdIdService().getValue().toString();
        String                   lsNomService = 
            getPoUpdNomService().getValue() == null ? "" : 
            getPoUpdNomService().getValue().toString();
        String                   lsDesService = 
            getPoUpdDesService().getValue() == null ? "" : 
            getPoUpdDesService().getValue().toString();
        String                   lsIndServiceWsdl = 
            getPoUpdWsdl().getValue() == null ? "" :
            getPoUpdWsdl().getValue().toString();
        String                   lsIndSystem = 
            getPoUpdSystem().getValue() == null ? "" : 
            getPoUpdSystem().getValue().toString();
        String                   lsIndOrigin = 
            getPoUpdSystemOr().getValue() == null ? "" : 
            getPoUpdSystemOr().getValue().toString();
        String                   lsIndDestiny = 
            getPoUpdSystemDes().getValue() == null ? "" :
            getPoUpdSystemDes().getValue().toString();
        String                   lsIndEstatus = 
            getPoUpdStatus().getValue() == null ? "" : 
            getPoUpdStatus().getValue().toString();
        String                   lsIndSynchronous = 
            getPoUpdAsyn().getValue() == null ? "" :
            getPoUpdAsyn().getValue().toString();
        String                   lsStatusTab = "0";
        if(lsIndEstatus.equalsIgnoreCase("true")){
            lsStatusTab = "1";
        }
        String                    lsAsynTab = "0";
        if(lsIndSynchronous.equalsIgnoreCase("true")){
            lsAsynTab = "1";
        }
        ApplicationModule          loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleImpl  loService = (AppModuleImpl)loAm;
        try{
            LmkIntServicesCatRowBean loLmkBean = new LmkIntServicesCatRowBean();
            loLmkBean.setLiIdService(Integer.parseInt(lsIdService));
            loLmkBean.setLsNomService(lsNomService);
            loLmkBean.setLsIndDescService(lsDesService);
            loLmkBean.setLsIndServiceWsdl(lsIndServiceWsdl);
            loLmkBean.setLsIndSystem(lsIndSystem);
            loLmkBean.setLsIndOrigin(lsIndOrigin);
            loLmkBean.setLsIndDestiny(lsIndDestiny);
            loLmkBean.setLsIndEstatus(lsStatusTab);
            loLmkBean.setLsIndSynchronous(lsAsynTab);
            
            loService.updateServicesCatModel(loLmkBean);            
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                  gsEntityIterator,
                                                  getPoTblMain()
                                                  );
        new UtilFaces().hidePopup(getPoPopupUpdate());
        
        return null;
    }
    
    /**
     * Oculta popup de Actualizacion
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String cancelUpdateAction() {
        getPoUpdNomService().setValue("");
        getPoUpdWsdl().setValue("");
        getPoUpdSystem().setValue("");
        getPoUpdSystemOr().setValue("");
        getPoUpdSystemDes().setValue("");
        getPoUpdStatus().setValue("");
        getPoUpdAsyn().setValue("");
        new UtilFaces().hidePopup(getPoPopupUpdate());        
        FacesContext       loContext = FacesContext.getCurrentInstance();
        ExternalContext    loEctx = loContext.getExternalContext();
        String             lsUrl = 
            loEctx.getRequestContextPath() + "/faces/processPage";
        try {
            loEctx.redirect(lsUrl);
        } catch (IOException loEx) {
            ;
        }
        return null;
    }
    
    /**
     * Regresa un conjunto de servicios web 
     * @autor Jorge Luis Bautista Santiago
     * @param tsStrSearch
     * @return List
     */
    public List getListWebServicesValid() {
        List<SelectItem>        laList = 
            new ArrayList<SelectItem>();    
        ViewObjectDao           loMd = new ViewObjectDao();
        List<SelectOneItemBean> laAllWs = 
            loMd.getListGeneralParametersModelFilter("INTEGRATION_WSERVICES");
        for(SelectOneItemBean loWs: laAllWs){
            SelectItem loItm = new SelectItem();            
            loItm.setValue(loWs.getLsValue());
            loItm.setDescription(loWs.getLsValue());
            loItm.setLabel(loWs.getLsDescription());
            laList.add(loItm);
        }
        return laList;
    }
    
    /**
     * Regresa un conjunto de servicios web 
     * @autor Jorge Luis Bautista Santiago
     * @param tsStrSearch
     * @return List
     */
    public List getListWebServices() {
        List<SelectItem>        laList = 
            new ArrayList<SelectItem>();
        ViewObjectDao           loMd = new ViewObjectDao();
        List<SelectOneItemBean> laAllWs = loMd.getListAllWebServicesModel();
        for(SelectOneItemBean loWs: laAllWs){
            SelectItem loItm = new SelectItem();            
            loItm.setValue(loWs.getLsId());
            loItm.setDescription(loWs.getLsValue());
            loItm.setLabel(loWs.getLsValue());
            laList.add(loItm);
        }
        return laList;
    }
    
    /**
     * Regresa un conjunto de servicios web 
     * @autor Jorge Luis Bautista Santiago
     * @param tsStrSearch
     * @return List
     */
    public List getListWebServicesValidSearch() {
        List<SelectItem>        laList = 
            new ArrayList<SelectItem>();    
        ViewObjectDao           loMd = new ViewObjectDao();
        List<SelectOneItemBean> laAllWs = 
            loMd.getListGeneralParametersModelFilter("INTEGRATION_WSERVICES");
        for(SelectOneItemBean loWs: laAllWs){
            SelectItem loItm = new SelectItem();            
            loItm.setValue(loWs.getLsValue());
            loItm.setDescription(loWs.getLsValue());
            loItm.setLabel(loWs.getLsDescription());
            laList.add(loItm);
        }
        return laList;
    }
    
    /*********** SETTERS AND GETTER ***********/
    
    public void setPoFilterDesServiceSel(RichSelectOneChoice poFilterDesServiceSel) {
        this.poFilterDesServiceSel = poFilterDesServiceSel;
    }

    public RichSelectOneChoice getPoFilterDesServiceSel() {
        return poFilterDesServiceSel;
    }

    public void setPoFilterServiceSel(RichSelectOneChoice poFilterServiceSel) {
        this.poFilterServiceSel = poFilterServiceSel;
    }

    public RichSelectOneChoice getPoFilterServiceSel() {
        return poFilterServiceSel;
    }

    public void setPoFilterService(RichInputText poFilterService) {
        this.poFilterService = poFilterService;
    }

    public RichInputText getPoFilterService() {
        return poFilterService;
    }

    public void setPoFilterWsdl(RichInputText poFilterWsdl) {
        this.poFilterWsdl = poFilterWsdl;
    }

    public RichInputText getPoFilterWsdl() {
        return poFilterWsdl;
    }

    public void setPoFilterSystem(RichSelectOneChoice poFilterSystem) {
        this.poFilterSystem = poFilterSystem;
    }

    public RichSelectOneChoice getPoFilterSystem() {
        return poFilterSystem;
    }

    public void setPoFilterSystemOr(RichSelectOneChoice poFilterSystemOr) {
        this.poFilterSystemOr = poFilterSystemOr;
    }

    public RichSelectOneChoice getPoFilterSystemOr() {
        return poFilterSystemOr;
    }

    public void setPoFilterSystemDes(RichSelectOneChoice poFilterSystemDes) {
        this.poFilterSystemDes = poFilterSystemDes;
    }

    public RichSelectOneChoice getPoFilterSystemDes() {
        return poFilterSystemDes;
    }

    public void setPoFilterAsynSel(RichSelectOneChoice poFilterAsynSel) {
        this.poFilterAsynSel = poFilterAsynSel;
    }

    public RichSelectOneChoice getPoFilterAsynSel() {
        return poFilterAsynSel;
    }

    public void setPoFilterStatusSel(RichSelectOneChoice poFilterStatusSel) {
        this.poFilterStatusSel = poFilterStatusSel;
    }

    public RichSelectOneChoice getPoFilterStatusSel() {
        return poFilterStatusSel;
    }

    public void setPoTblMain(RichTable poTblMain) {
        this.poTblMain = poTblMain;
    }

    public RichTable getPoTblMain() {
        return poTblMain;
    }

    public void setPoUpdIdService(RichInputText poUpdIdService) {
        this.poUpdIdService = poUpdIdService;
    }

    public RichInputText getPoUpdIdService() {
        return poUpdIdService;
    }

    public void setPoUpdNomService(RichInputText poUpdNomService) {
        this.poUpdNomService = poUpdNomService;
    }

    public RichInputText getPoUpdNomService() {
        return poUpdNomService;
    }

    public void setPoUpdDesService(RichInputText poUpdDesService) {
        this.poUpdDesService = poUpdDesService;
    }

    public RichInputText getPoUpdDesService() {
        return poUpdDesService;
    }   

    public void setPoUpdSystem(RichSelectOneChoice poUpdSystem) {
        this.poUpdSystem = poUpdSystem;
    }

    public RichSelectOneChoice getPoUpdSystem() {
        return poUpdSystem;
    }

    public void setPoUpdSystemOr(RichSelectOneChoice poUpdSystemOr) {
        this.poUpdSystemOr = poUpdSystemOr;
    }

    public RichSelectOneChoice getPoUpdSystemOr() {
        return poUpdSystemOr;
    }

    public void setPoPopupDelete(RichPopup poPopupDelete) {
        this.poPopupDelete = poPopupDelete;
    }

    public RichPopup getPoPopupDelete() {
        return poPopupDelete;
    }

    public void setPoDeleteMsgLbl(RichPanelLabelAndMessage poDeleteMsgLbl) {
        this.poDeleteMsgLbl = poDeleteMsgLbl;
    }

    public RichPanelLabelAndMessage getPoDeleteMsgLbl() {
        return poDeleteMsgLbl;
    }

    public void setPoDeleteIdBinding(RichOutputText poDeleteIdBinding) {
        this.poDeleteIdBinding = poDeleteIdBinding;
    }

    public RichOutputText getPoDeleteIdBinding() {
        return poDeleteIdBinding;
    }

    public void setPoPopupUpdate(RichPopup poPopupUpdate) {
        this.poPopupUpdate = poPopupUpdate;
    }

    public RichPopup getPoPopupUpdate() {
        return poPopupUpdate;
    }

    public void setPoUpdSystemDes(RichSelectOneChoice poUpdSystemDes) {
        this.poUpdSystemDes = poUpdSystemDes;
    }

    public RichSelectOneChoice getPoUpdSystemDes() {
        return poUpdSystemDes;
    }

    public void setPoUpdAsyn(RichSelectBooleanCheckbox poUpdAsyn) {
        this.poUpdAsyn = poUpdAsyn;
    }

    public RichSelectBooleanCheckbox getPoUpdAsyn() {
        return poUpdAsyn;
    }

    public void setPoUpdStatus(RichSelectBooleanCheckbox poUpdStatus) {
        this.poUpdStatus = poUpdStatus;
    }

    public RichSelectBooleanCheckbox getPoUpdStatus() {
        return poUpdStatus;
    }

    public void setPoPopupSave(RichPopup poPopupSave) {
        this.poPopupSave = poPopupSave;
    }

    public RichPopup getPoPopupSave() {
        return poPopupSave;
    }

    public void setPoSaveNomService(RichInputText poSaveNomService) {
        this.poSaveNomService = poSaveNomService;
    }

    public RichInputText getPoSaveNomService() {
        return poSaveNomService;
    }

    public void setPoSaveNomServiceSel(RichSelectOneChoice poSaveNomServiceSel) {
        this.poSaveNomServiceSel = poSaveNomServiceSel;
    }

    public RichSelectOneChoice getPoSaveNomServiceSel() {
        return poSaveNomServiceSel;
    }

    public void setPoSaveWsdl(RichInputText poSaveWsdl) {
        this.poSaveWsdl = poSaveWsdl;
    }

    public RichInputText getPoSaveWsdl() {
        return poSaveWsdl;
    }

    public void setPoSaveSystem(RichSelectOneChoice poSaveSystem) {
        this.poSaveSystem = poSaveSystem;
    }

    public RichSelectOneChoice getPoSaveSystem() {
        return poSaveSystem;
    }

    public void setPoSaveSystemOr(RichSelectOneChoice poSaveSystemOr) {
        this.poSaveSystemOr = poSaveSystemOr;
    }

    public RichSelectOneChoice getPoSaveSystemOr() {
        return poSaveSystemOr;
    }

    public void setPoSaveSystemDes(RichSelectOneChoice poSaveSystemDes) {
        this.poSaveSystemDes = poSaveSystemDes;
    }

    public RichSelectOneChoice getPoSaveSystemDes() {
        return poSaveSystemDes;
    }

    public void setPoSaveAsyn(RichSelectBooleanCheckbox poSaveAsyn) {
        this.poSaveAsyn = poSaveAsyn;
    }

    public RichSelectBooleanCheckbox getPoSaveAsyn() {
        return poSaveAsyn;
    }

    public void setPoSaveStatus(RichSelectBooleanCheckbox poSaveStatus) {
        this.poSaveStatus = poSaveStatus;
    }

    public RichSelectBooleanCheckbox getPoSaveStatus() {
        return poSaveStatus;
    }

    public void setPoUpdWsdl(RichInputText poUpdWsdl) {
        this.poUpdWsdl = poUpdWsdl;
    }

    public RichInputText getPoUpdWsdl() {
        return poUpdWsdl;
    }


    public void setPoPopupMerge(RichPopup poPopupMerge) {
        this.poPopupMerge = poPopupMerge;
    }

    public RichPopup getPoPopupMerge() {
        return poPopupMerge;
    }

    public void setPoMergeIdService(RichInputText poMergeIdService) {
        this.poMergeIdService = poMergeIdService;
    }

    public RichInputText getPoMergeIdService() {
        return poMergeIdService;
    }

    public void setPoMergeNomService(RichInputText poMergeNomService) {
        this.poMergeNomService = poMergeNomService;
    }

    public RichInputText getPoMergeNomService() {
        return poMergeNomService;
    }

    public void setPoMerge2can(RichSelectBooleanCheckbox poMerge2can) {
        this.poMerge2can = poMerge2can;
    }

    public RichSelectBooleanCheckbox getPoMerge2can() {
        return poMerge2can;
    }

    public void setPoMerge5can(RichSelectBooleanCheckbox poMerge5can) {
        this.poMerge5can = poMerge5can;
    }

    public RichSelectBooleanCheckbox getPoMerge5can() {
        return poMerge5can;
    }

    public void setPoMerge9can(RichSelectBooleanCheckbox poMerge9can) {
        this.poMerge9can = poMerge9can;
    }

    public RichSelectBooleanCheckbox getPoMerge9can() {
        return poMerge9can;
    }

    public void setPoMergeCliente(RichInputText poMergeCliente) {
        this.poMergeCliente = poMergeCliente;
    }

    public RichInputText getPoMergeCliente() {
        return poMergeCliente;
    }

    public String mergeAction() {        
        String                   lsIdService = 
            getPoMergeIdService().getValue() == null ? "" : 
            getPoMergeIdService().getValue().toString();
        
        String                     lsInd2can = 
            getPoMerge2can().getValue() == null ? "":
            getPoMerge2can().getValue().toString();
        String                     lslsInd2canTab = "0";
        if(lsInd2can.equalsIgnoreCase("true")){
            lslsInd2canTab = "1";
        }
        String                     lsInd5can = 
            getPoMerge5can().getValue() == null ? "":
            getPoMerge5can().getValue().toString();
        String                     lslsInd5canTab = "0";
        if(lsInd5can.equalsIgnoreCase("true")){
            lslsInd5canTab = "1";
        }
        String                     lsInd9can = 
            getPoMerge9can().getValue() == null ? "":
            getPoMerge9can().getValue().toString();
        String                     lsInd9canTab = "0";
        if(lsInd9can.equalsIgnoreCase("true")){
            lsInd9canTab = "1";
        }
        String                   lsCliente = 
            getPoMergeCliente().getValue() == null ? "" : 
            getPoMergeCliente().getValue().toString();
        
        ApplicationModule         loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleImpl loService = 
            (AppModuleImpl)loAm;        
        try{
            loService.deleteServicesParamsModelByServ(Integer.parseInt(lsIdService));
            System.out.println("Todos Eliminados ok");
            LmkIntServicesParamsRowBean loLmk2CanBean = new LmkIntServicesParamsRowBean();            
            Integer                  liId2can = 
                new ViewObjectDao().getMaxIdParadigm("ServParameters") + 1;
            loLmk2CanBean.setLiIdParameterServ(liId2can);
            loLmk2CanBean.setLiIdService(Integer.parseInt(lsIdService));
            loLmk2CanBean.setLsIndEstatus("1");
            loLmk2CanBean.setLsIndParameter("2CAN");
            loLmk2CanBean.setLsIndValParameter(lslsInd2canTab);                        
            loService.insertServicesParamsModel(loLmk2CanBean);  
            System.out.println("2CAN insert ok");
            LmkIntServicesParamsRowBean loLmk5CanBean = new LmkIntServicesParamsRowBean();            
            Integer                  liId5can = 
                new ViewObjectDao().getMaxIdParadigm("ServParameters") + 1;
            loLmk5CanBean.setLiIdParameterServ(liId5can);
            loLmk5CanBean.setLiIdService(Integer.parseInt(lsIdService));
            loLmk5CanBean.setLsIndEstatus("1");
            loLmk5CanBean.setLsIndParameter("5CAN");
            loLmk5CanBean.setLsIndValParameter(lslsInd5canTab);                        
            loService.insertServicesParamsModel(loLmk5CanBean);  
            System.out.println("5CAN insert ok");
            LmkIntServicesParamsRowBean loLmk9CanBean = new LmkIntServicesParamsRowBean();            
            Integer                  liId9can = 
                new ViewObjectDao().getMaxIdParadigm("ServParameters") + 1;
            loLmk9CanBean.setLiIdParameterServ(liId9can);
            loLmk9CanBean.setLiIdService(Integer.parseInt(lsIdService));
            loLmk9CanBean.setLsIndEstatus("1");
            loLmk9CanBean.setLsIndParameter("9CAN");
            loLmk9CanBean.setLsIndValParameter(lsInd9canTab);                        
            loService.insertServicesParamsModel(loLmk9CanBean);  
            System.out.println("9CAN insert ok");
            LmkIntServicesParamsRowBean loLmkCtenBean = new LmkIntServicesParamsRowBean();            
            Integer                  liIdCte = 
                new ViewObjectDao().getMaxIdParadigm("ServParameters") + 1;
            loLmkCtenBean.setLiIdParameterServ(liIdCte);
            loLmkCtenBean.setLiIdService(Integer.parseInt(lsIdService));
            loLmkCtenBean.setLsIndEstatus("1");
            loLmkCtenBean.setLsIndParameter("CLIENTE");
            loLmkCtenBean.setLsIndValParameter(lsCliente);                        
            loService.insertServicesParamsModel(loLmkCtenBean);  
            System.out.println("CLIENTE insert ok");
            FacesMessage loMsg;
            loMsg = new FacesMessage("Parámetros Asignados Satisfactoriamente");
            loMsg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        
        new UtilFaces().hidePopup(getPoPopupMerge());
        
        return null;
    }

    public String cancelMergeAction() {
        new UtilFaces().hidePopup(getPoPopupMerge());
        return null;
    }

    public void showAddParameters(ActionEvent loActionEvent) {
        loActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblMain().getSelectedRowData();
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();
        String                   lsDesService = 
            loNode.getAttribute("IndDescService") == null ? "" : 
            loNode.getAttribute("IndDescService").toString();
        
        getPoMergeIdService().setValue(lsIdService);        
        getPoMergeNomService().setValue(lsDesService);
        
        // Obtener valores si es que ya se han guardado y settear valores        
        getPoMerge2can().setSelected(false);        
        getPoMerge5can().setSelected(false);        
        getPoMerge9can().setSelected(false);   
        getPoMergeCliente().setValue(null);        
        new UtilFaces().showPopup(getPoPopupMerge());
    }
}
