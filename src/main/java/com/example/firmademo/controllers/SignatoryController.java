package com.example.firmademo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.firmademo.domain.Signatory;
import com.example.firmademo.dto.SignatoryDto;
import com.example.firmademo.services.SignatoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/signatory")
public class SignatoryController {

    @Autowired
    public SignatoryService signatoryService;

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("signatoryForm", new SignatoryDto());
        return "signatory/newSignatoryView";
    }
    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid 
            SignatoryDto signatoryForm,
            BindingResult bindingResult) {
                Signatory signatory = signatoryService.convertDtoToSignatory(signatoryForm);
                signatoryService.createSignatory(signatory);
        return "redirect:/signatory/list";
    }

    @GetMapping({ "/list" })
    public String showList(Model model) {
        model.addAttribute("signatoryList", signatoryService.getAllSignatories());
        return "signatory/listSignatoryView";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable Long id) {
        signatoryService.deleteSignatoryById(id);
        return "redirect:/signatory/list";
    }
}
