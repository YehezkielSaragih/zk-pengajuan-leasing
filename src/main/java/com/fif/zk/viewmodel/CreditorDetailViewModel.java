package com.fif.zk.viewmodel;

import com.fif.zk.dto.CreditorDetailResponse;
import com.fif.zk.model.Creditor;
import com.fif.zk.model.Loan;
import com.fif.zk.model.LoanStatus;
import com.fif.zk.service.CreditorService;
import com.fif.zk.service.LoanService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@VariableResolver(DelegatingVariableResolver.class)
public class CreditorDetailViewModel {

    // --- Services ---
    @WireVariable("creditorServiceImpl")
    private CreditorService creditorService;

    @WireVariable("loanServiceImpl")
    private LoanService loanService;

    // --- Fields ---
    private CreditorDetailResponse creditDetail;

    // --- Getters ---
    public CreditorDetailResponse getCreditDetail() {
        return creditDetail;
    }

    // --- Init ---
    @Init
    public void init() {
        String idParam = Executions.getCurrent().getParameter("id");
        if (idParam != null) {
            int creditorId = Integer.parseInt(idParam);
            Creditor creditor = creditorService.getCreditorById(creditorId);

            if (creditor != null) {
                List<Loan> loans = loanService.getLoansByCreditorId(creditorId)
                        .stream()
                        .filter(l -> l.getDeletedAt() == null)
                        .collect(Collectors.toList());

                int total = loans.size();
                int pending = (int) loans.stream().filter(l -> l.getStatus() == LoanStatus.PENDING).count();
                int approved = (int) loans.stream().filter(l -> l.getStatus() == LoanStatus.APPROVED).count();
                int rejected = (int) loans.stream().filter(l -> l.getStatus() == LoanStatus.REJECTED).count();
                creditDetail = new CreditorDetailResponse(creditor, total, pending, approved, rejected);
            }
        }
    }

    // --- Commands ---
    @Command
    public void save() {
        if (creditDetail != null && creditDetail.getCreditor() != null) {
            creditorService.updateCreditor(creditDetail.getCreditor());
        }
        Executions.sendRedirect("/pages/layout.zul?page=/pages/creditor-dashboard.zul");
    }

    @Command
    public void back() {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/creditor-dashboard.zul");
    }
}
