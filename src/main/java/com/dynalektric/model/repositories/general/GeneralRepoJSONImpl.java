package com.dynalektric.model.repositories.general;

import com.dynalektric.constants.SystemConstants;
import com.dynalektric.constants.ViewMessages;
import com.dynalektric.helpers.FileHelpers;
import com.dynalektric.helpers.FileIOHelper;
import com.dynalektric.helpers.JacksonFileIOHelper;
import com.dynalektric.model.Model;
import com.dynalektric.model.repositories.project.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Set;

public class GeneralRepoJSONImpl implements GeneralRepo{
    private final static Logger LOGGER = LogManager.getLogger(GeneralRepoJSONImpl.class);
    private final FileIOHelper fileIOHelper = new JacksonFileIOHelper();
    @Override
    public String getLoadedProjectName(){
        try{
            General generalData = (General)fileIOHelper.readData(new File(SystemConstants.GENERAL_FILE) , General.class);
            return generalData.loadedProject;
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            Model.getSingleton().notifyLiveView(ViewMessages.ERROR_OCCURRED);
        }
        return null;
    }

    @Override
    public void setLoadedProjectName(String name) {
        try{
            General generalData = new General();
            generalData.loadedProject = name;
            fileIOHelper.writeData(new File(SystemConstants.GENERAL_FILE) , generalData);
        }catch(Exception e){
            LOGGER.error(e.getMessage() , e);
            Model.getSingleton().notifyLiveView(ViewMessages.ERROR_OCCURRED);
        }
    }

    @Override
    public Set<String> getNamesOfAllProjectsCreated() {
        General general = (General) fileIOHelper.readData(new File(SystemConstants.GENERAL_FILE) , General.class);
        return general.createdProjectNames;
    }

    @Override
    public void deleteProjectByName(String name) {
        General general = (General) fileIOHelper.readData(new File(SystemConstants.GENERAL_FILE) , General.class);
        Set<String> projectsSet = general.createdProjectNames;
        projectsSet.remove(name);
        general.createdProjectNames = projectsSet;
        fileIOHelper.writeData(new File(SystemConstants.GENERAL_FILE) , general);
        System.out.println(SystemConstants.DATABASE_DIR+name+"json");
        FileHelpers.deleteFile(new File(SystemConstants.DATABASE_DIR+name+".json"));
    }

    @Override
    public void addNewCreatedProjectName(String name) {
        General general = (General) fileIOHelper.readData(new File(SystemConstants.GENERAL_FILE) , General.class);
        general.createdProjectNames.add(name);
        fileIOHelper.writeData(new File(SystemConstants.GENERAL_FILE) , general);
    }

    @Override
    public void createNewProject(Project project) {
        fileIOHelper.writeData(new File(SystemConstants.DATABASE_DIR+project.projectName+".json") , project);
    }

    @Override
    public Project getProjectByName(String name) {
        return null;
    }
}
