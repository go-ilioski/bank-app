package com.finki.bank.web.rest;

import com.finki.bank.service.TransactionService;
import com.finki.bank.service.dto.ResultTransactionDto;
import com.finki.bank.service.dto.TransactionDto;
import com.finki.bank.util.Constants;
import com.finki.bank.web.rest.errors.BadRequestAlertException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority(\"" + Constants.USER_ROLE + "\")")
    public ResponseEntity<ResultTransactionDto> processTransaction(@RequestBody TransactionDto transactionDto){
        if(transactionDto.getId() != null){
            throw new BadRequestAlertException();
        }

        ResultTransactionDto newDepositTransaction = transactionService.save(transactionDto);

        return ResponseEntity.status(HttpStatus.OK).body(newDepositTransaction);

    }

    @GetMapping("/{id}/search/page")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    public ResponseEntity<List<ResultTransactionDto>> getTransactionsPageable(@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate searchStartDate,
                                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate searchEndDate,
                                                                              @PathVariable Long id,
                                                                              Pageable pageable,
                                                                              @RequestParam(required = false) BigDecimal startAmount,
                                                                              @RequestParam(required = false) BigDecimal endAmount
    ) {
        Page<ResultTransactionDto> page = searchTransactions(searchStartDate, searchEndDate, id, pageable, startAmount, endAmount);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.TOTAL_COUNTS_HEADER, Long.toString(page.getTotalElements()));

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}/search/report")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    public void getTransactionsReportDownload(HttpServletResponse httpServletResponse,
                                              @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate searchStartDate,
                                              @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate searchEndDate,
                                              @PathVariable Long id,
                                              Pageable pageable,
                                              @RequestParam(required = false) BigDecimal startAmount,
                                              @RequestParam(required = false) BigDecimal endAmount
    ) throws JRException, IOException {
        Page<ResultTransactionDto> page = searchTransactions(searchStartDate, searchEndDate, id, pageable, startAmount, endAmount);

        httpServletResponse.setContentType("application/x-download");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"transactions.pdf\"");
        OutputStream out = httpServletResponse.getOutputStream();
        transactionService.exportReport(page.getContent(), out);
    }

    @GetMapping("/{id}/search/report/generate")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    public ResponseEntity<String> getTransactionsReportGenerate(@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate searchStartDate,
                                                                @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate searchEndDate,
                                                                @PathVariable Long id,
                                                                Pageable pageable,
                                                                @RequestParam(required = false) BigDecimal startAmount,
                                                                @RequestParam(required = false) BigDecimal endAmount
    ) throws JRException, IOException {
        Page<ResultTransactionDto> page = searchTransactions(searchStartDate, searchEndDate, id, pageable, startAmount, endAmount);

        String result = transactionService.exportReport(page.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private Page<ResultTransactionDto> searchTransactions(@DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(required = false) LocalDate searchStartDate, @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(required = false) LocalDate searchEndDate, @PathVariable Long id, Pageable pageable, @RequestParam(required = false) BigDecimal startAmount, @RequestParam(required = false) BigDecimal endAmount) {
        if (searchEndDate == null) {
            searchEndDate = LocalDate.now();
        }
        if (searchStartDate == null) {
            searchStartDate = searchEndDate.minusMonths(1);
        }
        if (searchStartDate.isAfter(LocalDate.now()) || searchEndDate.isBefore(searchStartDate)){
            throw new BadRequestAlertException("Invalid Date input");
        }

        return transactionService.searchPageable(pageable, searchStartDate,searchEndDate,id,startAmount,endAmount);
    }
}
