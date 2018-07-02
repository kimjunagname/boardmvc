package com.kitri.factory;

import com.kitri.action.Action;
import com.kitri.action.admin.board.*;

public class BoardAdminActionFactory {

	private static Action boardMenuAction;
	private static Action makeCategoryAction;
	private static Action makeBoardAction;
	
	static {
		boardMenuAction = BoardMenuAction.getBoardMenuAction();
		makeCategoryAction = MakeCategoryAction.getMakeCategoryAction();
		makeBoardAction = MakeBoardAction.getMakeBoardAction();
	}

	public static Action getBoardMenuAction() {
		return boardMenuAction;
	}

	public static Action getMakeCategoryAction() {
		return makeCategoryAction;
	}

	public static Action getMakeBoardAction() {
		return makeBoardAction;
	}
	
}













