package com.fif.zk.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class CreditorService {
    private static CreditorService instance;
    private List<CreditorViewModel.Kreditur> daftarKreditur;

    private CreditorService() {
        daftarKreditur = new ArrayList<>();
        // Dummy awal
        daftarKreditur.add(new CreditorViewModel.Kreditur("Andi", "Jakarta", "08123", 5000000));
        daftarKreditur.add(new CreditorViewModel.Kreditur("Budi", "Bandung", "08234", 7000000));
    }

    public static CreditorService getInstance() {
        if (instance == null) {
            instance = new CreditorService();
        }
        return instance;
    }

    public List<CreditorViewModel.Kreditur> getDaftarKreditur() {
        return daftarKreditur;
    }

    public void tambahKreditur(CreditorViewModel.Kreditur kreditur) {
        daftarKreditur.add(kreditur);
    }
}
