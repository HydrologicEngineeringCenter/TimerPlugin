/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerplugin;
import com.rma.factories.NewObjectFactory;
import hec.model.OutputVariable;
import hec2.map.GraphicElement;
import hec2.model.DataLocation;
import hec2.model.ProgramOrderItem;
import hec2.plugin.action.EditAction;
import hec2.plugin.action.OutputElement;
import hec2.plugin.lang.ModelLinkingException;
import hec2.plugin.lang.OutputException;
import hec2.plugin.model.ModelAlternative;
import hec2.wat.model.tracking.OutputPlugin;
import hec2.wat.plugin.AbstractSelfContainedWatPlugin;
import hec2.wat.plugin.CreatableWatPlugin;
import hec2.wat.plugin.WatPluginManager;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author WatPowerUser
 */
public class TimerPlugin extends AbstractSelfContainedWatPlugin<TimerAlternative> implements CreatableWatPlugin, OutputPlugin  {
    public static final String PluginName = "Timer Plugin";
    private static final String _pluginVersion = "0.0.1";
    private static final String _pluginSubDirectory = "TimerPlugin";
    private static final String _pluginExtension = ".tp";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TimerPlugin p = new TimerPlugin();
    }
    public TimerPlugin(){
        super();
        setName(PluginName);
        setProgramOrderItem(new ProgramOrderItem(PluginName,
                "A plugin to compute timings between plugins",
                false,1,"shortname","Images/fda/wsp.png"));
        WatPluginManager.register(this);
    }
    @Override
    protected String getAltFileExtension() {
        return _pluginExtension;
    }
    @Override
    public String getPluginDirectory() {
        return _pluginSubDirectory;
    }
    @Override
    public String getVersion() {
        return _pluginVersion;
    }
    @Override
    public boolean saveProject() {
        boolean success = true;
        for(TimerAlternative alt: _altList){
            if(!alt.saveData()){
                success = false;
                System.out.println("Alternative " + alt.getName() + " could not save");
            }
        }
        return success;
    }
    @Override
    protected TimerAlternative newAlternative(String string) {
        return new TimerAlternative(string);
    }
    @Override
    protected NewObjectFactory getAltObjectFactory() {
        return new TimerAlternativeFactory(this);
    }
    @Override
    public List<DataLocation> getDataLocations(ModelAlternative ma, int i) {
        TimerAlternative alt = getAlt(ma);
        if(alt==null)return null;
        if(DataLocation.INPUT_LOCATIONS == i){
            //input
            return alt.getInputDataLocations();
        }else{
            //ouput
            return alt.getOutputDataLocations();
        }
    }
    @Override
    public boolean setDataLocations(ModelAlternative ma, List<DataLocation> list) throws ModelLinkingException {
        TimerAlternative alt = getAlt(ma);
        return true;
    }
    @Override
    public boolean compute(ModelAlternative ma) {
        TimerAlternative alt = getAlt(ma);
        if(alt!=null){
            alt.setComputeOptions(ma.getComputeOptions());
            return alt.compute();
        }
        return false;
    }
    @Override
    public void editAlternative(TimerAlternative e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public boolean displayApplication() {
        return false;
    }
    @Override
    public List<GraphicElement> getGraphicElements(ModelAlternative ma) {
        return new ArrayList<>();
    }
    @Override
    public List<OutputElement> getOutputReports(ModelAlternative ma) {
        return new ArrayList<>();
    }
    @Override
    public boolean displayEditor(GraphicElement ge) {
        return false;
    }
    @Override
    public boolean displayOutput(OutputElement oe, List<ModelAlternative> list) throws OutputException {
        return false;
    }
    @Override
    public List<EditAction> getEditActions(ModelAlternative ma) {
        return new ArrayList<>();
    }
    @Override
    public void editAction(String string, ModelAlternative ma) {
        
    }

    @Override
    public List<OutputVariable> getAvailOutputVariables(ModelAlternative ma) {
        List<OutputVariable> ret = new ArrayList<>();
        TimerAlternative alt = getAlt(ma);
        return alt.getOutputVariables();

    }
    @Override
    public boolean computeOutputVariables(List<OutputVariable> list, ModelAlternative ma) { 
        TimerAlternative alt = getAlt(ma);
        return alt.computeOutputVariables(list);
    }

    @Override
    public boolean hasOutputVariables(ModelAlternative ma) {
        return getAlt(ma).hasOutputVariables();
    }

}
