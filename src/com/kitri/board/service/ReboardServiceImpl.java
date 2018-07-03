package com.kitri.board.service;

import java.util.*;

import com.kitri.board.dao.ReboardDaoImpl;
import com.kitri.board.model.ReboardDto;
import com.kitri.common.dao.CommonDaoImpl;
import com.kitri.util.KitriConstance;

public class ReboardServiceImpl implements ReboardService {
	
	private static ReboardService reboardService;
	
	static {
		reboardService = new ReboardServiceImpl();
	}
	
	private ReboardServiceImpl() {}

	public static ReboardService getReboardService() {
		return reboardService;
	}

	@Override
	public int writeArticle(ReboardDto reboardDto) {
		int seq = CommonDaoImpl.getCommonDao().getNextSeq();
		reboardDto.setSeq(seq);
		reboardDto.setRef(seq);
		return ReboardDaoImpl.getReboardDao().writeArticle(reboardDto) == 0 ? 0 : seq;
	}

	@Override
	public int replyArticle(ReboardDto reboardDto) {
		return 0;
	}

	@Override
	public List<ReboardDto> listArticle(int bcode, int pg, String key, String word) {
		int end = pg * KitriConstance.BOARD_LIST_SIZE;
		int start = end - KitriConstance.BOARD_LIST_SIZE;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("bcode", bcode + "");
		map.put("start", start + "");
		map.put("end", end + "");
		map.put("key", key);
		map.put("word", word);
		return ReboardDaoImpl.getReboardDao().listArticle(map);
	}

	@Override
	public ReboardDto viewArticle(int seq) {
		CommonDaoImpl.getCommonDao().updateHit(seq); //updata 서비스 호출
		ReboardDto reboardDto = ReboardDaoImpl.getReboardDao().viewArticle(seq);
		if(reboardDto != null)
			reboardDto.setContent(reboardDto.getContent().replaceAll("\n", "<br>"));
		return reboardDto;
	}

		
	@Override
	public ReboardDto getArticle(int seq) {
		System.out.println("getArticle" + seq);
		//ReboardDto reboardDto = ReboardDaoImpl.getReboardDao().viewArticle(seq);
		//if(reboardDto != null) {
		//	reboardDto.setContent(reboardDto.getContent().replaceAll("\n", "<br>"));
		//	System.out.println("service getSeq>>>>>>> " + reboardDto.getSeq());
		//	System.out.println("service getName>>>>>>> " + reboardDto.getName());
		//	System.out.println("service getContent>>>>>>> " + reboardDto.getContent());
		//	System.out.println("service getSubject>>>>>>> " + reboardDto.getSubject());
		//}
		return ReboardDaoImpl.getReboardDao().viewArticle(seq);
	}

	//글수정 > 컨트롤러 > 액션(mvmodify) > 서비스(여기 - getArticle(int seq)) > DAO > modify.jsp > 이동
	
	@Override
	public int modifyArticle(ReboardDto reboardDto) {
		
		return ReboardDaoImpl.getReboardDao().modifyArticle(reboardDto);
	}

	@Override
	public int deleteArticle(ReboardDto reboardDto) {
		return ReboardDaoImpl.getReboardDao().deleteArticle(reboardDto);

	}

}
