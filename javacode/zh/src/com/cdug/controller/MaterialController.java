package com.cdug.controller;

import java.util.ArrayList;

import com.cdug.config.GlobalConfig;
import com.cdug.model.Files;
import com.cdug.model.Materials;
import com.cdug.model.Solutions;
import com.cdug.model.Technicals;
import com.cdug.tool.UITools;
import com.jfinal.core.Controller;

public class MaterialController extends Controller {
	public void index() {
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_MATERIAL);
		// get all material
		if ("GET".equals(getRequest().getMethod())) {
			setAttr("materials", Materials.dao.getAllMaterial());
			setAttr("solutions", Solutions.dao.getSolutions());
			setAttr("technicals", Technicals.dao.getTechnicals());
			render("/page/material/materials.html");
		} else {
			String[] teids = UITools
					.convertIdsValue(getParaValues("technical"));
			String[] soids = UITools.convertIdsValue(getParaValues("solution"));
			ArrayList<Solutions> so_list = Solutions.dao.getSolutions();
			ArrayList<Technicals> te_list = Technicals.dao.getTechnicals();
			for(Solutions so:so_list){
				for(String id:soids){
					if(so.get("id").toString().equals(id)){
						so.setChecked(true);
					}
				}
			}
			
			for(Technicals te:te_list){
				for(String id:teids){
					if(te.get("id").toString().equals(id)){
						te.setChecked(true);
					}
				}
			}
			setAttr("solutions",so_list);
			setAttr("technicals", te_list);
			setAttr("materials", Materials.dao.getAllMaterial(teids, soids));
			render("/page/material/materials.html");
		}
	}

	public void content() {
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_MATERIAL);
		int id = Integer.parseInt(getPara(0));
		setAttr("material", Materials.dao.findById(id));
		setAttr("material_files", Files.dao.getFilesByMaterialId(id));
		render("/page/material/material-content.html");
	}
}
