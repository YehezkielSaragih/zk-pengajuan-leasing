package com.fif.zk.viewmodel;

import com.fif.zk.dto.CreditorDetailResponse;
import com.fif.zk.model.Creditor;
import com.fif.zk.model.Loan;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import com.fif.zk.service.implementation.LoanServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import java.util.List;
import java.util.stream.Collectors;

public class CreditorDetailViewModel {

    private CreditorDetailResponse creditDetail;

    public CreditorDetailResponse getCreditDetail() { return creditDetail; }

    @Init
    public void init() {
        String idParam = Executions.getCurrent().getParameter("id");
        if (idParam != null) {
            int creditorId = Integer.parseInt(idParam);
            Creditor creditor = CreditorServiceImpl.getInstance().getCreditorById(creditorId);

            if (creditor != null) {
                List<Loan> loans = LoanServiceImpl.getInstance()
                        .getLoansByCreditorId(creditorId)
                        .stream()
                        .filter(l -> l.getDeletedAt() == null)
                        .collect(Collectors.toList());

                int total = loans.size();
                int pending = (int) loans.stream().filter(l -> "Pending".equalsIgnoreCase(l.getStatus())).count();
                int approved = (int) loans.stream().filter(l -> "Approved".equalsIgnoreCase(l.getStatus())).count();
                int rejected = (int) loans.stream().filter(l -> "Rejected".equalsIgnoreCase(l.getStatus())).count();

                creditDetail = new CreditorDetailResponse(creditor, total, pending, approved, rejected);
            }
        }
    }

    @Command
    public void save() {
        if (creditDetail != null && creditDetail.getCreditor() != null) {
            CreditorServiceImpl.getInstance().updateCreditor(creditDetail.getCreditor());
        }
        Executions.sendRedirect("/pages/layout.zul?page=/pages/creditorDashboard.zul");
    }

    @Command
    public void back() {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/creditorDashboard.zul");
    }
}
