/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timerplugin;
import com.rma.io.DssFileManagerImpl;
import com.rma.io.RmaFile;
import hec.data.Parameter;
import hec.heclib.dss.DSSPathname;
import hec.heclib.dss.HecDSSDataAttributes;
import hec.io.DSSIdentifier;
import hec.io.TimeSeriesContainer;
import hec.model.OutputVariable;
import hec2.model.DataLocation;
import hec2.plugin.model.ComputeOptions;
import hec2.plugin.selfcontained.SelfContainedPluginAlt;
import hec2.wat.client.WatFrame;
import hec2.wat.model.OutputElementImpl;
import hec2.wat.model.tracking.OutputVariableImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
/**
 *
 * @author WatPowerUser
 */
public class TimerAlternative extends SelfContainedPluginAlt{
    private List<DataLocation> _dataLocations = new ArrayList<>();
    private String _pluginVersion;
    private static final String DocumentRoot = "TimerAlternative";
    private static final String OutputVariableElement = "OutputVariables";
    private static final String AlternativeNameAttribute = "Name";
    private static final String AlternativeDescriptionAttribute = "Desc";
    private ComputeOptions _computeOptions;
 //   private List<DurationOutputVariable> _outputVariables;
    private List<OutputVariableImpl> _outputVariables;
    public TimerAlternative(){
        super();
        _dataLocations = new ArrayList<>();
    }
    public TimerAlternative(String name){
        this();
        setName(name);
    }
    @Override
    public boolean saveData(RmaFile file){
        if(file!=null){
            Element root = new Element(DocumentRoot);
            root.setAttribute(AlternativeNameAttribute,getName());
            root.setAttribute(AlternativeDescriptionAttribute,getDescription());
            Document doc = new Document(root);
            return writeXMLFile(doc,file);
        }
        return false;
    }
    @Override
    protected boolean loadDocument(org.jdom.Document dcmnt) {
        if(dcmnt!=null){
            org.jdom.Element ele = dcmnt.getRootElement();
            if(ele==null){
                System.out.println("No root element on the provided XML document.");
                return false;   
            }
            if(ele.getName().equals(DocumentRoot)){
                setName(ele.getAttributeValue(AlternativeNameAttribute));
                setDescription(ele.getAttributeValue(AlternativeDescriptionAttribute));
            }else{
                System.out.println("XML document root was imporoperly named.");
                return false;
            }
            if(_dataLocations==null){
                _dataLocations = new ArrayList<>();
            }
            _dataLocations.clear();
            setModified(false);
            return true;
        }else{
            System.out.println("XML document was null.");
            return false;
        }
    }

    public List<DataLocation> getOutputDataLocations(){
       //construct output data locations 
        List<DataLocation> ret = new ArrayList<>();
	return ret;//defaultDataLocations();
    }
    public List<DataLocation> getInputDataLocations(){
            return _dataLocations;
    }
    public List<DataLocation> getDataLocations(){
        return _dataLocations;
    }
    public void setComputeOptions(ComputeOptions opts){
        _computeOptions = opts;
    }
    @Override
    public boolean isComputable() {
        return true;
    }
    @Override
    public boolean compute() {
        return true;
    }
    @Override
    public boolean cancelCompute() {
        return false;
    }
    @Override
    public String getLogFile() {
        return null;
    }
    @Override
    public int getModelCount() {
        return 1;
    }
    
    public List<OutputVariable> getOutputVariables(){
        
        List<OutputVariable> ret = new ArrayList<>();
        OutputVariableImpl o = new OutputVariableImpl();
        o.setName(this.getName());
        o.setDescription("Compute Timing");
        o.setParamId(Parameter.PARAMID_TIMING);
        ret.add(o);
        return ret;
    }
    public boolean hasOutputVariables(){
        return true;
    }

    boolean computeOutputVariables(List<OutputVariable> list) {
        //we could sort into groups by datalocations to minimize reads.
        for(OutputVariable o : list){
            OutputVariableImpl oimpl = (OutputVariableImpl)o;
            java.util.Date d= new java.util.Date();
            oimpl.setValue((double) d.getTime());
        }
        return true;
    }
}
