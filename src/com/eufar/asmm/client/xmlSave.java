package com.eufar.asmm.client;

import static com.google.gwt.query.client.GQuery.$;

import java.util.List;
import java.util.logging.Level;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

public class xmlSave {
	

	// create a string composed of xml code
	public static String createXml() {

		Asmm_eufar.rootLogger.log(Level.INFO, "Creation of the XML code in progress...");
		String xmlString = null;
		try {
			Document doc = XMLParser.createDocument();
			Element rootElement = doc.createElement("asmm:MissionMetadata");
			rootElement.setAttribute("xmlns:asmm","http://www.eufar.net/ASMM");
			Element xcreationDate = doc.createElement("asmm:CreationDate");
			xcreationDate.appendChild(doc.createTextNode(Asmm_eufar.creationDate));
			rootElement.appendChild(xcreationDate);
			Element xrevisionDate = doc.createElement("asmm:RevisionDate");
			xrevisionDate.appendChild(doc.createTextNode(Asmm_eufar.revisionDate));
			rootElement.appendChild(xrevisionDate);


			//////////////////////////
			/// Flight information ///
			//////////////////////////
			Element flightInformation = doc.createElement("asmm:FlightInformation");
			Element flightNumber = doc.createElement("asmm:FlightNumber");
			flightNumber.appendChild(doc.createTextNode(Asmm_eufar.fi_flightText.getText()));
			flightInformation.appendChild(flightNumber);
			Element flightDate = doc.createElement("asmm:Date");
			String value = new String(DateTimeFormat.getFormat("yyyy-MM-dd").format(Asmm_eufar.fi_dateText.getValue()));
			flightDate.appendChild(doc.createTextNode(value));
			flightInformation.appendChild(flightDate);
			Element flightCampaign = doc.createElement("asmm:ProjectAcronym");
			flightCampaign.appendChild(doc.createTextNode(Asmm_eufar.fi_campaignText.getText()));
			flightInformation.appendChild(flightCampaign);
			Element missionScientist = doc.createElement("asmm:MissionScientist");
			missionScientist.appendChild(doc.createTextNode(Asmm_eufar.fi_scientistText.getText()));
			flightInformation.appendChild(missionScientist);
			Element flightManager = doc.createElement("asmm:FlightManager");
			flightManager.appendChild(doc.createTextNode(Asmm_eufar.fi_managerText.getText()));
			flightInformation.appendChild(flightManager);
			if (Asmm_eufar.horizontalPanel26.getWidgetCount() > 1) {
				Element flightAircraft = doc.createElement("asmm:Platform");
				flightAircraft.appendChild(doc.createTextNode(Asmm_eufar.fi_otherAiText.getText()));
				flightInformation.appendChild(flightAircraft);			
				Element flightOperator = doc.createElement("asmm:Operator");
				flightOperator.appendChild(doc.createTextNode(Asmm_eufar.fi_otherOpsText.getText()));
				flightInformation.appendChild(flightOperator);
			} else {
				if (Asmm_eufar.fi_operatorText.getSelectedItemText() == "Make a choice...") {
					Element flightAircraft = doc.createElement("asmm:Platform");
					flightAircraft.appendChild(doc.createTextNode(""));
					flightInformation.appendChild(flightAircraft);			
					Element flightOperator = doc.createElement("asmm:Operator");
					flightOperator.appendChild(doc.createTextNode(""));
					flightInformation.appendChild(flightOperator);
				} else {
					int k = 0;
					int j = 0;
					for (int i = 0; i < Asmm_eufar.operatorsAircraft.length; i++) {
						if (Asmm_eufar.fi_aircraftText.getSelectedItemText() == Asmm_eufar.operatorsAircraft[i][1]) {
							k = 1;
							Element flightAircraft = doc.createElement("asmm:Platform");
							flightAircraft.appendChild(doc.createTextNode(Asmm_eufar.operatorsAircraft[i][3]));
							flightInformation.appendChild(flightAircraft);
							break;
						}
					}
					for (int i = 0; i < Asmm_eufar.operatorsAircraft.length; i++) {
						if (Asmm_eufar.fi_operatorText.getSelectedItemText() == Asmm_eufar.operatorsAircraft[i][0]) {
							j = 1;
							Element flightOperator = doc.createElement("asmm:Operator");
							flightOperator.appendChild(doc.createTextNode(Asmm_eufar.operatorsAircraft[i][2]));
							flightInformation.appendChild(flightOperator);
							break;
						}
					}
					if (k == 0) {
						Element flightAircraft = doc.createElement("asmm:Platform");
						flightAircraft.appendChild(doc.createTextNode(""));
						flightInformation.appendChild(flightAircraft);
					}
					if (j == 0) {
						Element flightOperator = doc.createElement("asmm:Operator");
						flightOperator.appendChild(doc.createTextNode(""));
						flightInformation.appendChild(flightOperator);
					}

				}

			}
			Element flightCountry = doc.createElement("asmm:Localisation");
			if (Asmm_eufar.geoDetailLst.getSelectedItemText() != null) {
				flightCountry.appendChild(doc.createTextNode(Asmm_eufar.geoDetailLst.getSelectedItemText()));
			} else {
				flightCountry.appendChild(doc.createTextNode(""));
			}
			flightInformation.appendChild(flightCountry);
			rootElement.appendChild(flightInformation);


			///////////////////////////
			/// Contact information ///
			///////////////////////////
			Element contactInformation = doc.createElement("asmm:ContactInfo");
			Element contactName = doc.createElement("asmm:ContactName");
			contactName.appendChild(doc.createTextNode(Asmm_eufar.ci_nameText.getText()));
			contactInformation.appendChild(contactName);
			Element contactRole = doc.createElement("asmm:ContactRole");
			if (Asmm_eufar.ci_roleText.getSelectedItemText() == "Make a choice...") {
				contactRole.appendChild(doc.createTextNode(""));
			} else {
				contactRole.appendChild(doc.createTextNode(Asmm_eufar.ci_roleText.getSelectedItemText()));
			}
			contactInformation.appendChild(contactRole);
			Element contactEmail = doc.createElement("asmm:ContactEmail");
			contactEmail.appendChild(doc.createTextNode(Asmm_eufar.ci_emailText.getText()));
			contactInformation.appendChild(contactEmail);
			rootElement.appendChild(contactInformation);


			///////////////////////
			/// Scientific aims ///
			///////////////////////
			Element scientificAims = doc.createElement("asmm:ScientificAims");
			List<CheckBox> allCheckBox = $("*", Asmm_eufar.saScroll).widgets(CheckBox.class);
			for (int i = 0; i < allCheckBox.size(); i = i + 2) {
				if (allCheckBox.get(i).getValue()) {
					String stringCode = Asmm_eufar.scientificMap.get(allCheckBox.get(i).getName());
					if (stringCode == null) {
						stringCode = Asmm_eufar.sa_addCatMap.get(allCheckBox.get(i).getName());
						Element saCode = doc.createElement("asmm:SA_User");
						saCode.appendChild(doc.createTextNode(stringCode));
						scientificAims.appendChild(saCode);
					} else {
						Element saCode = doc.createElement("asmm:SA_Code");
						saCode.appendChild(doc.createTextNode(stringCode));
						scientificAims.appendChild(saCode);
					}
				}
			}
			Element saComments = doc.createElement("asmm:SA_Other");
			saComments.appendChild(doc.createTextNode(Asmm_eufar.sa_comArea.getText()));
			scientificAims.appendChild(saComments);
			rootElement.appendChild(scientificAims);


			////////////////////////////////
			/// Geographical Information ///
			////////////////////////////////
			Element geographicalInformation = doc.createElement("asmm:GeographicalRegion");
			Element boundingBox = doc.createElement("asmm:GeographicBoundingBox");
			Element westBound = doc.createElement("asmm:westBoundLongitude");
			westBound.appendChild(doc.createTextNode(Asmm_eufar.gi_westText.getText()));
			boundingBox.appendChild(westBound);
			Element eastBound = doc.createElement("asmm:eastBoundLongitude");
			eastBound.appendChild(doc.createTextNode(Asmm_eufar.gi_eastText.getText()));
			boundingBox.appendChild(eastBound);
			Element northBound = doc.createElement("asmm:northBoundLatitude");
			northBound.appendChild(doc.createTextNode(Asmm_eufar.gi_northText.getText()));
			boundingBox.appendChild(northBound);
			Element southBound = doc.createElement("asmm:southBoundLatitude");
			southBound.appendChild(doc.createTextNode(Asmm_eufar.gi_southText.getText()));
			boundingBox.appendChild(southBound);
			Element minBound = doc.createElement("asmm:minAltitude");
			minBound.appendChild(doc.createTextNode(Asmm_eufar.gi_minText.getText()));
			boundingBox.appendChild(minBound);
			Element maxBound = doc.createElement("asmm:maxAltitude");
			maxBound.appendChild(doc.createTextNode(Asmm_eufar.gi_maxText.getText()));
			boundingBox.appendChild(maxBound);
			geographicalInformation.appendChild(boundingBox);
			allCheckBox = $("*", Asmm_eufar.giScroll).widgets(CheckBox.class);
			for (int i = 0; i < allCheckBox.size(); i = i + 2) {
				if (allCheckBox.get(i).getValue()) {
					String stringCode = Asmm_eufar.geographicMap.get(allCheckBox.get(i).getName());
					if (stringCode == null) {
						stringCode = Asmm_eufar.gi_addCatMap.get(allCheckBox.get(i).getName());
						Element code = doc.createElement("asmm:GR_User");
						code.appendChild(doc.createTextNode(stringCode));
						geographicalInformation.appendChild(code);
					} else {
						Element giCode = doc.createElement("asmm:GR_Code");
						giCode.appendChild(doc.createTextNode(stringCode));
						geographicalInformation.appendChild(giCode);
					}
				}
			}
			Element giComments = doc.createElement("asmm:GR_Other");
			giComments.appendChild(doc.createTextNode(Asmm_eufar.gi_comArea.getText()));
			geographicalInformation.appendChild(giComments);
			rootElement.appendChild(geographicalInformation);


			/////////////////////////////////////
			/// Atmospheric Synoptic Features ///
			/////////////////////////////////////
			Element atmosphericFeatures = doc.createElement("asmm:AtmosFeatures");
			allCheckBox = $("*", Asmm_eufar.afScroll).widgets(CheckBox.class);
			for (int i = 0; i < allCheckBox.size(); i = i + 2) {
				if (allCheckBox.get(i).getValue()) {
					String stringCode = Asmm_eufar.synopticMap.get(allCheckBox.get(i).getName());
					if (stringCode == null) {
						stringCode = Asmm_eufar.af_addCatMap.get(allCheckBox.get(i).getName());
						Element code = doc.createElement("asmm:AF_User");
						code.appendChild(doc.createTextNode(stringCode));
						atmosphericFeatures.appendChild(code);
					} else {
						Element afCode = doc.createElement("asmm:AF_Code");
						afCode.appendChild(doc.createTextNode(Asmm_eufar.synopticMap.get(allCheckBox.get(i).getName())));
						atmosphericFeatures.appendChild(afCode);
					}
				}
			}
			Element afComments = doc.createElement("asmm:AF_Other");
			afComments.appendChild(doc.createTextNode(Asmm_eufar.af_comArea.getText()));
			atmosphericFeatures.appendChild(afComments);
			rootElement.appendChild(atmosphericFeatures);


			///////////////////////////////////////////////////
			/// Cloud Types and Forms Sampled During Flight ///
			///////////////////////////////////////////////////
			Element cloudTypes = doc.createElement("asmm:CloudTypes");
			allCheckBox = $("*", Asmm_eufar.ctScroll).widgets(CheckBox.class);
			for (int i = 0; i < allCheckBox.size(); i = i + 2) {
				if (allCheckBox.get(i).getValue()) {
					String stringCode = Asmm_eufar.cloudMap.get(allCheckBox.get(i).getName());
					if (stringCode == null) {
						stringCode = Asmm_eufar.ct_addCatMap.get(allCheckBox.get(i).getName());
						Element code = doc.createElement("asmm:CT_User");
						code.appendChild(doc.createTextNode(stringCode));
						cloudTypes.appendChild(code);
					} else {
						Element ctCode = doc.createElement("asmm:CT_Code");
						ctCode.appendChild(doc.createTextNode(Asmm_eufar.cloudMap.get(allCheckBox.get(i).getName())));
						cloudTypes.appendChild(ctCode);
					}
				}
			}
			Element ctComments = doc.createElement("asmm:CT_Other");
			ctComments.appendChild(doc.createTextNode(Asmm_eufar.ct_comArea.getText()));
			cloudTypes.appendChild(ctComments);
			rootElement.appendChild(cloudTypes);


			//////////////////////////////////////////////////////////
			/// Cloud, Precipitation and Aerosol Particles Sampled ///
			//////////////////////////////////////////////////////////
			Element cpapTypes = doc.createElement("asmm:ParticlesSampled");
			allCheckBox = $("*", Asmm_eufar.cpScroll).widgets(CheckBox.class);
			for (int i = 0; i < allCheckBox.size(); i = i + 2) {
				if (allCheckBox.get(i).getValue()) {
					String stringCode = Asmm_eufar.cpapMap.get(allCheckBox.get(i).getName());
					if (stringCode == null) {
						stringCode = Asmm_eufar.cp_addCatMap.get(allCheckBox.get(i).getName());
						Element code = doc.createElement("asmm:PS_User");
						code.appendChild(doc.createTextNode(stringCode));
						cpapTypes.appendChild(code);
					} else {
						Element cpCode = doc.createElement("asmm:PS_Code");
						cpCode.appendChild(doc.createTextNode(Asmm_eufar.cpapMap.get(allCheckBox.get(i).getName())));
						cpapTypes.appendChild(cpCode);
					}
				}
			}
			Element cpComments = doc.createElement("asmm:PS_Other");
			cpComments.appendChild(doc.createTextNode(Asmm_eufar.cp_comArea.getText()));
			cpapTypes.appendChild(cpComments);
			rootElement.appendChild(cpapTypes);


			/////////////////////////////////////////
			/// Land or Oceans Surfaces Overflown ///
			/////////////////////////////////////////
			Element surfacesOverflown = doc.createElement("asmm:SurfacesOverflown");
			allCheckBox = $("*", Asmm_eufar.loScroll).widgets(CheckBox.class);
			for (int i = 0; i < allCheckBox.size(); i = i + 2) {
				if (allCheckBox.get(i).getValue()) {
					String stringCode = Asmm_eufar.surfacesMap.get(allCheckBox.get(i).getName());
					if (stringCode == null) {
						stringCode = Asmm_eufar.lo_addCatMap.get(allCheckBox.get(i).getName());
						Element code = doc.createElement("asmm:SO_User");
						code.appendChild(doc.createTextNode(stringCode));
						surfacesOverflown.appendChild(code);
					} else {
						Element soCode = doc.createElement("asmm:SO_Code");
						soCode.appendChild(doc.createTextNode(Asmm_eufar.surfacesMap.get(allCheckBox.get(i).getName())));
						surfacesOverflown.appendChild(soCode);
					}
				}
			}
			Element soComments = doc.createElement("asmm:SO_Other");
			soComments.appendChild(doc.createTextNode(Asmm_eufar.lo_comArea.getText()));
			surfacesOverflown.appendChild(soComments);
			rootElement.appendChild(surfacesOverflown);


			/////////////////////////////////////
			/// Altitude Range of Measurement ///
			/////////////////////////////////////
			Element rangeMeasurement = doc.createElement("asmm:AltitudeRanges");
			allCheckBox = $("*", Asmm_eufar.arScroll).widgets(CheckBox.class);
			for (int i = 0; i < allCheckBox.size(); i = i + 2) {
				if (allCheckBox.get(i).getValue()) {
					String stringCode = Asmm_eufar.measurementMap.get(allCheckBox.get(i).getName());
					if (stringCode == null) {
						stringCode = Asmm_eufar.ar_addCatMap.get(allCheckBox.get(i).getName());
						Element code = doc.createElement("asmm:AR_User");
						code.appendChild(doc.createTextNode(stringCode));
						rangeMeasurement.appendChild(code);
					} else {
						Element arCode = doc.createElement("asmm:AR_Code");
						arCode.appendChild(doc.createTextNode(Asmm_eufar.measurementMap.get(allCheckBox.get(i).getName())));
						rangeMeasurement.appendChild(arCode);
					}
				}
			}
			Element arComments = doc.createElement("asmm:AR_Other");
			arComments.appendChild(doc.createTextNode(Asmm_eufar.ar_comArea.getText()));
			rangeMeasurement.appendChild(arComments);
			rootElement.appendChild(rangeMeasurement);


			/////////////////////////////////
			/// Types of Flight Manoeuvre ///
			/////////////////////////////////
			Element flightManoeuvre = doc.createElement("asmm:FlightTypes");
			allCheckBox = $("*", Asmm_eufar.fmScroll).widgets(CheckBox.class);
			for (int i = 0; i < allCheckBox.size(); i = i + 2) {
				if (allCheckBox.get(i).getValue()) {
					String stringCode = Asmm_eufar.manoeuvreMap.get(allCheckBox.get(i).getName());
					if (stringCode == null) {
						stringCode = Asmm_eufar.fm_addCatMap.get(allCheckBox.get(i).getName());
						Element code = doc.createElement("asmm:FT_User");
						code.appendChild(doc.createTextNode(stringCode));
						flightManoeuvre.appendChild(code);
					} else {
						Element fmCode = doc.createElement("asmm:FT_Code");
						fmCode.appendChild(doc.createTextNode(Asmm_eufar.manoeuvreMap.get(allCheckBox.get(i).getName())));
						flightManoeuvre.appendChild(fmCode);
					}
				}
			}
			Element fmComments = doc.createElement("asmm:FT_Other");
			fmComments.appendChild(doc.createTextNode(Asmm_eufar.fm_comArea.getText()));
			flightManoeuvre.appendChild(fmComments);
			rootElement.appendChild(flightManoeuvre);


			//////////////////////////////
			/// Satellite Coordination ///
			//////////////////////////////
			Element satelliteCoordination = doc.createElement("asmm:SatelliteCoordination");
			allCheckBox = $("*", Asmm_eufar.scScroll).widgets(CheckBox.class);
			for (int i = 0; i < allCheckBox.size(); i = i + 2) {
				if (allCheckBox.get(i).getValue()) {
					String stringCode = Asmm_eufar.satelliteMap.get(allCheckBox.get(i).getName());
					if (stringCode == null) {
						stringCode = Asmm_eufar.sc_addCatMap.get(allCheckBox.get(i).getName());
						Element code = doc.createElement("asmm:SC_User");
						code.appendChild(doc.createTextNode(stringCode));
						satelliteCoordination.appendChild(code);
					} else {
						Element scCode = doc.createElement("asmm:SC_Code");
						scCode.appendChild(doc.createTextNode(Asmm_eufar.satelliteMap.get(allCheckBox.get(i).getName())));
						satelliteCoordination.appendChild(scCode);
					}
				}
			}
			Element scComments = doc.createElement("asmm:SC_Other");
			scComments.appendChild(doc.createTextNode(Asmm_eufar.sc_comArea.getText()));
			satelliteCoordination.appendChild(scComments);
			rootElement.appendChild(satelliteCoordination);


			/////////////////////////////////////////////
			/// Supporting Surface-based Observations ///
			/////////////////////////////////////////////
			Element surfaceObservations = doc.createElement("asmm:SurfaceObs");
			for (int i = 0; i < Asmm_eufar.so_groundSitesList.size(); i++) {
				Element item1 = doc.createElement("asmm:GroundSite");
				item1.appendChild(doc.createTextNode(Asmm_eufar.so_groundSitesList.get(i)));
				surfaceObservations.appendChild(item1);
			}
			for (int i = 0; i < Asmm_eufar.so_researchVesselsList.size(); i++) {
				Element item2 = doc.createElement("asmm:ResearchVessel");
				item2.appendChild(doc.createTextNode(Asmm_eufar.so_researchVesselsList.get(i)));
				surfaceObservations.appendChild(item2);
			}
			for (int i = 0; i < Asmm_eufar.so_armSitesList.size(); i++) {
				Element item3 = doc.createElement("asmm:ArmSite");
				item3.appendChild(doc.createTextNode(Asmm_eufar.so_armSitesList.get(i)));
				surfaceObservations.appendChild(item3);
			}
			for (int i = 0; i < Asmm_eufar.so_mobileSitesList.size(); i++) {
				Element item4 = doc.createElement("asmm:ArmMobile");
				item4.appendChild(doc.createTextNode(Asmm_eufar.so_mobileSitesList.get(i)));
				surfaceObservations.appendChild(item4);
			}
			rootElement.appendChild(surfaceObservations);


			//////////////////////////////////////
			/// Additional Notes on the Flight ///
			//////////////////////////////////////
			Element additionalNotes = doc.createElement("asmm:OtherComments");
			additionalNotes.appendChild(doc.createTextNode(Asmm_eufar.nf_comArea.getText()));
			rootElement.appendChild(additionalNotes);


			doc.appendChild(rootElement);
			xmlString = "<?xml version='1.0' encoding='UTF-8'?>" + doc.toString();
			Asmm_eufar.rootLogger.log(Level.INFO, "Creation of the XML code finished...");
		} catch (Exception ex) {
			Asmm_eufar.rootLogger.log(Level.SEVERE, "A problem occured: ", ex);
		}
		return xmlString;
	}
}