package com.example.firmademo.controllers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.firmademo.domain.Document;
import com.example.firmademo.domain.Signatory;
import com.example.firmademo.services.DocumentService;
import com.example.firmademo.services.SignatoryService;
import com.example.firmademo.services.SignatureService;

@Controller
@RequestMapping("/signature")
public class SignatureController {

    @Autowired
    public SignatureService signatureService;
    @Autowired
    public DocumentService documentService;
    @Autowired
    public SignatoryService signatoryService;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        List<Document> documents = documentService.getAllDocuments();
        List<Signatory> signatories = signatoryService.getAllSignatories();
        model.addAttribute("documents", documents);
        model.addAttribute("signatories", signatories);
        return "signature/newSignatureView";
    }

    @PostMapping("/new/submit")
    public String showCreateFormSubmit(@RequestParam Long documentId, @RequestParam Long signatoryId) {
        signatureService.createSignatureWithIds(documentId, signatoryId);
        return "redirect:/signature/list";
    }

    @GetMapping({ "/list" })
    public String showList(Model model) {
        model.addAttribute("signatureList", signatureService.getAllSignatures());
        model.addAttribute("signatoriesList", signatoryService.getAllSignatories());
        return "signature/listSignatureView";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable Long id) {
        signatureService.deleteSignaturetById(id);
        return "redirect:/signature/list";
    }

    @GetMapping({ "/verify" })
    public String showVerifyForm(@RequestParam(required = false) String verificationResult, Model model) {
        model.addAttribute("signatureList", signatureService.getAllSignatures());
        model.addAttribute("signatoriesList", signatoryService.getAllSignatories());
        if (verificationResult != null) {
            model.addAttribute("verificationResult", verificationResult);
        }
        return "signature/verifySignatureView";
    }

    @PostMapping("/verify/submit")
    public String verifySignature(
            @RequestParam MultipartFile fileToVerify,
            @RequestParam Long signatoryId,
            @RequestParam Long signatureId,
            Model model) {
        String resultMessage;
        boolean isValid = signatureService.verifySignature(fileToVerify, signatoryId, signatureId);
        resultMessage = isValid ? "Valid signature" : "The signed document does not match the original. Invalid signature";
        return "redirect:/signature/verify?verificationResult="
                + java.net.URLEncoder.encode(resultMessage, StandardCharsets.UTF_8);
    }

}
