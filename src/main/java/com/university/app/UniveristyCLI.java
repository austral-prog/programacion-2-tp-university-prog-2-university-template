package com.university.app;

import com.university.CLI;
import com.university.CRUDRepository;

public class UniveristyCLI implements CLI {
    public static void main(String[] args) {
        // Create an instance of the UniversityCLI class
        UniveristyCLI universityCLI = new UniveristyCLI();

        // Run the CLI
        CRUDRepository<?>[] crudInterfaces = new CRUDRepository<?>[0];
        universityCLI.runCLI(crudInterfaces);
    }

    @Override
    public void runCLI(CRUDRepository<?>[] crudInterfaces) {

    }
}
