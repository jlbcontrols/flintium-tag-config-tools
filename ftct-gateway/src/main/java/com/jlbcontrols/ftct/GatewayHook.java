package com.jlbcontrols.ftct;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.common.tags.model.TagProvider;
import com.inductiveautomation.ignition.gateway.clientcomm.ClientReqSession;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Entry point for the module on the gateway
 */
public class GatewayHook extends AbstractGatewayModuleHook {
    public static final String MODULE_DESCRIPTION = "Flintium Tag Config Tools";
    private final Logger log;
    private static GatewayContext context;
    private final GatewayScriptModule scriptModule = new GatewayScriptModule();

    public GatewayHook() {
        log = LogManager.getLogger(this.getClass());
    }

    @Override
    public void setup(GatewayContext context) {
        try {
            GatewayHook.context = context;
        } catch (Exception e) {
            log.fatal(String.format("Error setting up %s module.",MODULE_DESCRIPTION), e);
        }
    }

    public static TagProvider getDefaultProvider() {
        return context.getTagManager().getTagProvider("default");
    }
    public static GatewayContext getContext() {
        return context;
    }

    @Override
    public void startup(LicenseState activationState) {
        try {
            log.info("Tag Script module started.");
        } catch (Exception e) {
            log.fatal(String.format("Error starting %s module.",MODULE_DESCRIPTION), e);
        }

    }

    @Override
    public void shutdown() {
        log.info(String.format("%s module stopped.",MODULE_DESCRIPTION));
    }

    @Override
    public void initializeScriptManager(ScriptManager manager) {
        super.initializeScriptManager(manager);

        manager.addScriptModule(
                "system.udtHelper",
                scriptModule,
                new PropertiesFileDocProvider());
    }

    @Override
    public Object getRPCHandler(ClientReqSession session, String projectName) {
        return scriptModule;
    }
}
