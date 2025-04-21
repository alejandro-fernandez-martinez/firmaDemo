package com.example.firmademo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.firmademo.domain.Document;
import com.example.firmademo.domain.Signatory;
import com.example.firmademo.domain.Signature;
import com.example.firmademo.dto.DocumentDto;
import com.example.firmademo.services.DocumentService;
import com.example.firmademo.services.SignatoryService;
import com.example.firmademo.services.SignatureService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    public DocumentService documentService;
    @Autowired
    public SignatoryService signatoryService;
    @Autowired
    public SignatureService signatureService;

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("documentForm", new DocumentDto());
        return "document/newDocumentView";
    }
    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid 
            DocumentDto documentForm,
            BindingResult bindingResult,
            @RequestParam MultipartFile fileAttach) {
                Document document = documentService.convertDtoToDocument(documentForm);
                documentService.createDocument(document);

        return "redirect:/document/list";
    }

    @GetMapping({ "/list" })
    public String showList(Model model) {
        model.addAttribute("documentList", documentService.getAllDocuments());
        return "document/listDocumentView";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        Document document = documentService.getDocumentById(id);
        List<Signatory> signatories = signatoryService.getAllSignatories();
        List<Signature> signatures = signatureService.getSignaturesByDocument(document);
        model.addAttribute("document", document);
        model.addAttribute("signatories", signatories);
        model.addAttribute("signatures", signatures);
        return "document/detailDocumentView";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable Long id) {
        documentService.deleteDocumentById(id);
        return "redirect:/document/list";
    }
}
