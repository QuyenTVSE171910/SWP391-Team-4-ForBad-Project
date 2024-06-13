package com.Court.CRUD.Controller;


import com.Court.CRUD.DTO.CourtDTO;
import com.Court.CRUD.Model.Court;
import com.Court.CRUD.Service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CourtController {
    private CourtService courtServ;

    @Autowired
    public CourtController(CourtService courtServ){
        this.courtServ = courtServ;
    }
    @GetMapping("/courts")
    public String listCourts (Model model){
        List<CourtDTO> courts = courtServ.findAllCourts();
        model.addAttribute("courts", courts);
        return "courts";
    }

    @GetMapping("/courts/create-court")
    public String createCourt (Model model){
        Court court = new Court();
        model.addAttribute("court", court);
        return "create-court";
    }

    @PostMapping("/courts/create-court")
    public String saveCourt (@ModelAttribute("court") Court court){
        courtServ.save(court);
        return "redirect:/courts";
    }

    @GetMapping("/courts/{court_id}/update-court")
    public String updateCourt (@PathVariable("court_id") String court_id,Model model){
        CourtDTO court = courtServ.findCourtByID(court_id);
        model.addAttribute("court", court);
        return "update-court";
    }

    @PostMapping("/courts/{court_id}/update-court")
    public String updateCourt(@PathVariable("court_id") String court_id,@ModelAttribute("court") CourtDTO court){
        court.setCourt_id(court_id);
        courtServ.updateCourt(court);
        return "redirect:/courts";
    }

    @GetMapping("/courts/{court_id}/delete")
    public String deleteCourt (@PathVariable("court_id") String court_id){
        courtServ.delete(court_id);
        return "redirect:/courts";
    }

}
