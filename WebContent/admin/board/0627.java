 


---2교시---
/board 일반
/reboard 답변
/album 사진
/bbs 자료실
/memo 

doc > board_create_table 참고

쿼리.sql에 아래 쿼리 집어넣어둔다 한곳에 모아서 보관한다. 나중에xml을 통해서 이용한다.
--------------------sql 쿼리문-------------------------
select b.bcode, b.bname, b.btype, c.ccode, c.cname,

	case
		when b.btype = 5 then 'reboard'
		when b.btype = 6 then 'album'
		when b.btype = 6 then 'bbs'
	end control

from board_list b, category c
where b.ccode = c.ccode
order by bcode asc
------------------------------------------------------

db를 써야할지, javascript를 서야할지, jstl을 써야할지 상황마다 잘 생각해봐야한다. 어떤게 서버에 부하가 덜가는지.

-------------------------------------------------------
jsp에서 컨트롤러 갈떄는 act포함 5개를 갖고 가.
컨트롤러에서 뷰페이지 갈때는 act는 필요없어~ bcode, pg, key, word만 갖고 가~

-객체 한개 하면 싱글톤 생각나야 된다.

-writeaction 6개 다 싱글톤 만들고 / 팩토리 보드액션에 겟 메소드

-