package com.drcome.project.main.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drcome.project.main.service.MainService;
import com.drcome.project.main.service.ReservationVO;
import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalService;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;


@Controller
public class MainController {
	
	@Autowired
	MainService mainService;
	
	@Autowired
	HospitalService hospitalService;
	
	
	
	
	//병원&약국 목록
	@GetMapping(value ={"/", "/home"})//주소
	public String getHosList(Model model) {
		List<HospitalVO> hosList = mainService.getHosList();
		model.addAttribute("hosList", hosList);
		List<PharmacyVO> phaList = mainService.getPhaList(5);
		model.addAttribute("phaList", phaList);
		//System.out.println(phaList);
		return "user/home";//폴더밑에 html 이름
	}
	
	
	//병원 상세페이지 //java로 세션값 받아오는 법도 있음(HttpServletRequest request)
	@GetMapping("/hospitalDetail")
	public String hosInformation(HttpServletRequest request, String hospitalId, Model model) { //String hospitalId 이게 get으로 링크에서 받은 값
		//병원정보
		HospitalVO hosInfo = mainService.getHos(hospitalId);
		//System.out.println("hosInfo"+ hosInfo);
		model.addAttribute("hosInfo", hosInfo);
		
		//병원의 의사정보
		List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId); 
		model.addAttribute("docList", docList);
		//System.out.println("docList="+docList);
		
		//받아온 세션값
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		//System.out.println("userId="+userId);
		
		//예약전 초진기록 확인
		int clinicHistory = mainService.checkClinicHistory(userId, hospitalId);
		model.addAttribute("clinicHistory", clinicHistory);
		//System.out.println("clinicHistory="+clinicHistory);
		
		//동일병원 당일 예약(진료받기전까지/ 예약한거 진료받고나면 예약ok)중복방지
		int reservationHistory = mainService.checkReservationHistory(userId, hospitalId);
		model.addAttribute("reservationHistory", reservationHistory);
		System.out.println("reservationHistory="+reservationHistory);
		return "user/hosDetail";
	}
		
	
	//약국 상세페이지
	@GetMapping("/pharmacyDetail")
	public String phaInformation(String pharmacyId, Model model) {
		PharmacyVO phaInfo = mainService.getPha(pharmacyId);
		//System.out.println("phaInfo" + phaInfo);
		model.addAttribute("phaInfo", phaInfo);
		return "user/phaDetail";
	}
	
	//병원 및 약국검색(리스트 2개 같이 보여줄거임)
	@GetMapping("/search")
	public String searchList(String word, Model model) {
		List<HospitalVO> searchHosList = mainService.searchHosList(word);
		model.addAttribute("searchHos", searchHosList );
		//System.out.println("searchHosList =" + searchHosList);
		List<PharmacyVO> searchPhaList = mainService.searchPhaList(word);
		model.addAttribute("searchPha", searchPhaList);
		//System.out.println("searchPhaList = " + searchPhaList);
		model.addAttribute("word", word);
		return "user/search";
	}
	
	/*@Override 중간때 검색 참고용
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		String path = "restaurant/restaurantSearch.tiles";
		
		String word = req.getParameter("word");
		
		RestaurantService svc = new RestaurantServiceImpl();
		List<RestaurantVO> list = svc.selectSearchList(word);
		
		req.setAttribute("searchWord", word);
		req.setAttribute("mlist", list);
		
		try {
			req.getRequestDispatcher(path).forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	//진료과목별 병원검색
	@GetMapping("/searchSubject")
	public String searchSubject(String mainSubject, Model model) {
		List<HospitalVO> subjectHosList = mainService.searchSubjectHos(mainSubject);
		model.addAttribute("mainSubject", mainSubject);
		model.addAttribute("subject", subjectHosList);
		System.out.println("mainSubject="+ mainSubject);
		System.out.println("subjectHosList="+ subjectHosList);
		return "user/searchSubject";
	}
	
	//진료 완료 후, 내 근처 추천약국 리스트
	@GetMapping("/recommendPharmacy")
	public String PhaList(Model model) {
		List<PharmacyVO> phaList = mainService.getPhaList(10);
		model.addAttribute("clinic","20");
		model.addAttribute("phaList", phaList);
		return "user/recommendPha";
	}
	
	//약국추천리스트 중 약국선택테이블에 insert
	@PostMapping("/recommendPharmacy")
	@ResponseBody
	public boolean insertPhaSelect(@RequestBody Map<String, Object> data) {
		Object pharmacyIdList = data.get("pharmacyId");
		 List<Object> pharmacyIdListAsList = (List<Object>) pharmacyIdList;
		    
		    // List를 String 배열로 변환
		String[] pharmacyIdArray = pharmacyIdListAsList.toArray(new String[0]);
		int clinicNo = Integer.parseInt((String) data.get("clinicNo"));
		int result = 0;
		//System.out.println("pharmacyIdArray="+pharmacyIdArray);
		for(int i=0; i < pharmacyIdArray.length; i++) {
			result += mainService.insertPhaSelect(pharmacyIdArray[i], clinicNo);
		}
		//System.out.println("result=" + result);
		if(result == 0) { //insert 안되면 false
			return false;
		}else { //insert되면 true
			return true;
		}
	}
	
	//방문예약페이지(select)
	@GetMapping("/contactReserve")
	public String contactReservationPage(HttpServletRequest request, String hospitalId, Model model, ReservationVO reservationVo ) {
		//병원정보
		HospitalVO hosInfo = mainService.getHos(hospitalId);
		//병원이름&아이디
		model.addAttribute("hosName", hosInfo.getHospitalName());
		model.addAttribute("hospitalId", hospitalId);
		//병원의 의사정보
		List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId); 
		model.addAttribute("docList", docList);
		//System.out.println("docList="+docList);
		
		//세션으로 유저아이디 가져옴
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		model.addAttribute("userId", userId);
		//System.out.println("userId="+userId);
		
		//병원 휴무일 보내기(숫자형태로 전환해서 보내는 중)
		Map<String, Integer> dayList = new HashMap<>();
		dayList.put("i1", 1);//월
		dayList.put("i2", 2);//화
		dayList.put("i3", 3);//수
		dayList.put("i4", 4);//목
		dayList.put("i5", 5);//금
		dayList.put("i6", 6);//토
		dayList.put("i7", 0);//일
		String date = hosInfo.getHoliday();
		//System.out.println("date="+date);
		//System.out.println(dayList.get("i1"));
		if(date.length() < 3){
			List<Integer> newDate = new ArrayList<>();
			newDate.add(dayList.get(date));
			System.out.println("newDate요일하나"+newDate);
			model.addAttribute("newDate", newDate);
		}else {
			String[] sliceDate = date.split(",");
			List<Integer> newDate = new ArrayList<>();
			for(int i=0; i < sliceDate.length; i++) {
				newDate.add(dayList.get(sliceDate[i]));
			}
			System.out.println("newDate요일여러개="+newDate);
			model.addAttribute("newDate", newDate);
		}
		
		return "user/contactReserve";
	}
	
	//방문예약페이지(insert)
	@PostMapping("/contactReserve")
	@ResponseBody
	public boolean insertReservation(@RequestBody ReservationVO reservationVo) {
		//System.out.println("reservationVo"+reservationVo);
		int result = mainService.insertContactReservation(reservationVo);
		if(result == 0) { //insert 안되면 false
			return false;
		}else { //insert되면 true
			return true;
		}
	}
	
	//모든예약페이지(이미 예약된 시간보여주기)
	@PostMapping("/reserveList")
	@ResponseBody
	public List<ReservationVO> selectReserveList(@RequestBody ReservationVO reservationVo) {
		//선택한 의사의 해당날짜 예약시간 보여주기
		List<ReservationVO> reserveTimeList = mainService.findreserveListToChoice(reservationVo);
//		for(int i=0; i < reserveTimeList.size(); i++) {
//			System.out.println("reserveTimeList="+ reserveTimeList.get(i).getReserveTime());
//		}
		return reserveTimeList;
	}
	
	//비대면예약페이지(select)
		@GetMapping("/untactReserve")
		public String unntactReservationPage(HttpServletRequest request, String hospitalId, Model model, ReservationVO reservationVo ) {
			//병원정보
			HospitalVO hosInfo = mainService.getHos(hospitalId);
			//병원이름&아이디
			model.addAttribute("hosName", hosInfo.getHospitalName());
			model.addAttribute("hospitalId", hospitalId);
			//병원의 의사정보
			List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId); 
			model.addAttribute("docList", docList);
			//System.out.println("docList="+docList);
			
			//세션으로 유저아이디 가져옴
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			model.addAttribute("userId", userId);
			//System.out.println("userId="+userId);
			
			//병원 휴무일 보내기(숫자형태로 전환해서 보내는 중)
			Map<String, Integer> dayList = new HashMap<>();
			dayList.put("i1", 1);//월
			dayList.put("i2", 2);//화
			dayList.put("i3", 3);//수
			dayList.put("i4", 4);//목
			dayList.put("i5", 5);//금
			dayList.put("i6", 6);//토
			dayList.put("i7", 0);//일
			String date = hosInfo.getHoliday();
			//System.out.println("date="+date);
			//System.out.println(dayList.get("i1"));
			if(date.length() < 3){
				List<Integer> newDate = new ArrayList<>();
				newDate.add(dayList.get(date));
				System.out.println("newDate요일하나"+newDate);
				model.addAttribute("newDate", newDate);
			}else {
				String[] sliceDate = date.split(",");
				List<Integer> newDate = new ArrayList<>();
				for(int i=0; i < sliceDate.length; i++) {
					newDate.add(dayList.get(sliceDate[i]));
				}
				System.out.println("newDate요일여러개="+newDate);
				model.addAttribute("newDate", newDate);
			}
			
			return "user/untactReserve";
		}
		
		//비대면예약페이지(insert)
		@PostMapping("/untactReserve")
		@ResponseBody
		public boolean insertUntactReservation(@RequestBody ReservationVO reservationVo) {
			//System.out.println("reservationVo"+reservationVo);
			int result = mainService.insertUntactReservation(reservationVo);
			System.out.println("insertUntactReservation="+result);
			if(result == 0) { //insert 안되면 false
				return false;
			}else { //insert되면 true
				return true;
			}
		}
		
		
		//비대면실시간접수페이지(select)
		@GetMapping("/untactAccept")
		public String untactAcceptPage(HttpServletRequest request, String hospitalId, Model model) {
			//병원정보
			HospitalVO hosInfo = mainService.getHos(hospitalId);
			//병원이름&아이디
			model.addAttribute("hosName", hosInfo.getHospitalName());
			model.addAttribute("hospitalId", hospitalId);
			//병원의 의사정보
			List<DoctorVO> docList = hospitalService.getDoctorAll(hospitalId); 
			model.addAttribute("docList", docList);
			//System.out.println("docList="+docList);
			
			//세션으로 유저아이디 가져옴
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			model.addAttribute("userId", userId);
			//System.out.println("userId="+userId);
			
			//병원 휴무일 보내기(숫자형태로 전환해서 보내는 중)
			Map<String, Integer> dayList = new HashMap<>();
			dayList.put("i1", 1);//월
			dayList.put("i2", 2);//화
			dayList.put("i3", 3);//수
			dayList.put("i4", 4);//목
			dayList.put("i5", 5);//금
			dayList.put("i6", 6);//토
			dayList.put("i7", 0);//일
			String date = hosInfo.getHoliday();
			//System.out.println("date="+date);
			//System.out.println(dayList.get("i1"));
			if(date.length() < 3){
				List<Integer> newDate = new ArrayList<>();
				newDate.add(dayList.get(date));
				System.out.println("newDate요일하나"+newDate);
				model.addAttribute("newDate", newDate);
			}else {
				String[] sliceDate = date.split(",");
				List<Integer> newDate = new ArrayList<>();
				for(int i=0; i < sliceDate.length; i++) {
					newDate.add(dayList.get(sliceDate[i]));
				}
				System.out.println("newDate요일여러개="+newDate);
				model.addAttribute("newDate", newDate);
			}
			
			return "user/untactAccept";
		}
				
		//비대면실시간접수페이지(insert)
		@PostMapping("/untactAccept")
		@ResponseBody
		public boolean insertUntactAccept(@RequestBody ReservationVO reservationVo) {
			//System.out.println("reservationVo"+reservationVo);
			int result = mainService.insertUntactReservation(reservationVo);
			System.out.println("insertUntactReservation="+result);
			if(result == 0) { //insert 안되면 false
				return false;
			}else { //insert되면 true
				return true;
			}
		}
		
		//비대면실시간접수페이지(대기현황)
		@PostMapping("/waitingList")
		@ResponseBody
		public int findWaitingList(@RequestBody DoctorVO doctorVO, Model model) {
			System.out.println("///////////////////////////////////////////");
			//System.out.println("doctorVO"+doctorVO);
			List<ReservationVO> findWaitingList = mainService.findWaitingList(doctorVO);
			List<String> times = new ArrayList<>();//옛날 시간 넣을 곳
			List<String> newTimes = new ArrayList<>();//concat한 시간 넣을곳
			String lastReservation = null; //내가 할 수 있는 가장 빠른 예약시간의 바로 앞전 시간
			int waitingNum = 0;//대기인원
			
			//19:00와 같은 옛날시간 times배열에 넣음
			for(int i=0; i < findWaitingList.size(); i++) {
				//System.out.println("findWaitingList="+ findWaitingList.get(i).getReserveTime());
				times.add(findWaitingList.get(i).getReserveTime());
			}
			//System.out.println("findWaitingList ="+ times);
			
			//1900와 같이 옛날시간 가공해서 newTimes배열에 넣음
			for(int i=0; i < times.size(); i++) {
				String hour = (times.get(i)).substring(0, 2);
				String minute = (times.get(i)).substring(3, 5);
				newTimes.add(hour.concat(minute));
//				System.out.println("(times.get(i))="+(times.get(i)));
//				System.out.println("hour="+hour);
//				System.out.println("minute="+minute);
			}
			
			//만약 배열의 값이 1개 이상이면
			if(newTimes.size() > 1) {
				for(int i= 0; i < (newTimes.size()-1) ; i++) {
					int extraNum = Integer.parseInt(newTimes.get(i + 1)) - Integer.parseInt(newTimes.get(i));
					System.out.println("extraNum= "+extraNum);
					int minutes = Integer.parseInt((newTimes.get(i)).substring(1, 3));
					
					//만약 예약의 값이 70넘게 차이나고(바로 다음 예약이 없다는 뜻) && 분이 30분 단위로 처리되면
					if(extraNum > 70 && (minutes) == 30) {
						waitingNum = i + 1; //대기인원
						model.addAttribute("waitingNum", waitingNum);
						lastReservation = newTimes.get(i);
						model.addAttribute("lastReservation", lastReservation);
					
					//만약 예약의 값이 70이상 차이나고(바로 다음 예약이 없다는 뜻) && 분이 30분 단위로 처리되지 않으면
					}else if(extraNum > 70 && (minutes) == 00) {
						
					}
				}
				
			}
			System.out.println("newTimes = "+newTimes.size());
			return 0;
		}


}
