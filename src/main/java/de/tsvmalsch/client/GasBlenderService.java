package de.tsvmalsch.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.tsvmalsch.shared.CalcResult;
import de.tsvmalsch.shared.CylinderContents;

@RemoteServiceRelativePath("calc")
public interface GasBlenderService extends RemoteService {

	CalcResult calcReal(CylinderContents StartCyl, CylinderContents TargetCyl,
			double CylVolume, int TempCelcius, boolean AddHeFirst);

	CalcResult calcIdeal(CylinderContents startCyl, CylinderContents targetCyl);

	CalcResult calcIdeal(CylinderContents startCyl, CylinderContents targetCyl,
			double topUpO2Mix);
}
