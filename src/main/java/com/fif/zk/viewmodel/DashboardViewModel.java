package com.fif.zk.viewmodel;

import java.util.List;

public class DashboardViewModel {
    public List<CreditorViewModel.Kreditur> getDaftarKreditur() {
        return CreditorService.getInstance().getDaftarKreditur();
    }
}
