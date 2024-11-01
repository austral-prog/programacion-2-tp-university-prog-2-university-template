package com.university;

public interface CLI {
    /**
     * Runs the Command Line Interface (CLI) for interacting with multiple entity types.
     * This method allows users to perform CRUD operations on various entity types by selecting the appropriate
     * entity type and operation via the command line.
     *
     * @param crudInterfaces an array of CRUDInterface instances, each representing a different entity type.
     *                       Each CRUDInterface allows the CLI to perform Create, Read, Update, and Delete
     *                       operations on that specific entity type.
     */
    void runCLI(CRUDRepository<?>[] crudInterfaces);
}
