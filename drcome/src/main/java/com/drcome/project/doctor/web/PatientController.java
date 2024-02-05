package com.drcome.project.doctor.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.doctor.service.PatientService;
import com.drcome.project.doctor.service.PatientVO;
import com.drcome.project.medical.service.HospitalService;

@Controller
public class PatientController {

	@Autowired
//	HospitalRepository repo;
	HospitalService hospitalService;

	// 공통 병원 정보 따로 빼기
	@ModelAttribute("hospitalSel")
	public Hospital getServerTime() {
		String hospitalId = "krrlo";
		Hospital hosSel = hospitalService.findByhospitalId(hospitalId);
		return hosSel;
	}

	@Autowired
	PatientService patientService;

	// 비대면진료페이지
	@GetMapping("untactClinic")
	public String getUntactInfo(PatientVO vo, Model model) {

		// 기본정보 // 입장버튼 눌렀을때 reserve no 받아오기
		vo.setReserveNo(4);
		PatientVO findVO = patientService.getPatientInfo(vo);
		// System.out.println(findVO);
		model.addAttribute("pInfo", findVO);

		// 진료기록리스트 //입장버튼 눌렀을때 hid uid 받아오기
		List<PatientVO> clinicList = patientService.getClinicList("krrlo", "test3");
		model.addAttribute("clist", clinicList);
		// System.out.println(clinicList);
		return "doctor/untactClinic";

	}

	// 대면진료페이지
	@GetMapping("clinic")
	public String getInfo(PatientVO vo, Model model) {
		PatientVO findVO = patientService.getPatientInfo(vo);
		model.addAttribute("pInfo", findVO);
		// System.out.println(findVO);
		return "doctor/clinic";

	}

	// 처방전 조회
	@GetMapping("perscription/{no}")
	@ResponseBody
	public List<PatientVO> perscriptionInfo(@PathVariable Integer no) {
		PatientVO vo = new PatientVO();
		vo.setClinicNo(no);
		// System.out.println(vo);
		return patientService.getPerscription(vo);
	}

	// 약검색
	@GetMapping("medicine")
	@ResponseBody
	public List<PatientVO> msearch(PatientVO vo) {
		// System.out.println(vo);
		return patientService.getmnameList(vo);

	}

	// 진료기록 저장
	@PostMapping("saveClinic")
	@ResponseBody
	public String saveInfo(@RequestBody PatientVO vo) {

		// System.out.println(vo);

		List<PatientVO> plist = vo.getPerary();

		// 재진이면
		if (vo.getVisit().equals("second")) {
			// 환자번호 select
			int pno = patientService.getPno(vo);
			// vo에 set
			vo.setPatientNo(pno);
			// 진료기록 insert 한후 insertid 받아오기
			int cno = patientService.insertClinic(vo);
			// 처방전이있다면
			if (vo.getPerscriptionYn() == null) {
				for (PatientVO obj : plist) {
					obj.setClinicNo(cno);
					patientService.insertPer(obj);

				}
			}
		} else {
			// 초진이면 신규환자등록
			patientService.patientInsert(vo);
			// 환자번호 select
			int pno = patientService.getPno(vo);
			// vo에 set
			vo.setPatientNo(pno);
			// 진료기록 insert 한후 insertid 받아오기
			int cno = patientService.insertClinic(vo);
			// 처방전이있다면
			if (vo.getPerscriptionYn() == null) {
				for (PatientVO obj : plist) {
					obj.setClinicNo(cno);
					patientService.insertPer(obj);
				}
			}
		} // else

		return "ddd";
	}

}// end class
