package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Begin;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.resources.AppMessages;
import org.jboss.examples.deltaspike.expensetracker.data.ExpenseRepository;
import org.jboss.examples.deltaspike.expensetracker.data.ReceiptRepository;
import org.jboss.examples.deltaspike.expensetracker.data.resources.Main;
import org.jboss.examples.deltaspike.expensetracker.domain.logic.Rules;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Expense;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Receipt;
import org.jboss.examples.deltaspike.expensetracker.view.SecuredPages;
import org.richfaces.event.FileUploadEvent;

@Controller
public class ReceiptController implements Serializable {

    @Inject
    private ReceiptRepository repo;

    @Inject
    private ExpenseRepository expenseRepo;

    @Inject
    private ViewStack viewStack;

    @Inject
    private JsfMessage<AppMessages> msg;

    @Inject
    @CurrentUser
    private Employee currentEmployee;

    @Inject
    private Event<ExpenseReportController.Modified> reportModEvent;

    @Inject
    private UploadController uploadCtl;

    private Receipt selected;

    private List<ExpenseCoverage> coverageMap = new ArrayList<ExpenseCoverage>();

    @Begin
    public Class<? extends ViewConfig> create(ExpenseReport report) {
        uploadCtl.clear();
        selected = new Receipt();
        selected.setReport(report);
        return SecuredPages.Receipt.class;
    }

    @Begin
    public Class<? extends ViewConfig> edit(Receipt receipt) {
        selected = receipt;
        return SecuredPages.Receipt.class;
    }

    public void delete(Receipt receipt) {
        for (Expense e : expenseRepo.findByReceipt(receipt)) {
            e.setReceipt(null);
            expenseRepo.save(e);
        }
        repo.remove(repo.merge(receipt));
        msg.addInfo().itemDeleted();
        reportModEvent.fire(new ExpenseReportController.Modified());
    }

    public void save() {
        if (isNew(selected)) {
            selected.setImportedBy(currentEmployee);
        }
        selected.setDocument(uploadCtl.getData());
        selected.setDocumentName(uploadCtl.getFileName());
        repo.saveAndFlush(selected);
        reportModEvent.fire(new ExpenseReportController.Modified());
        msg.addInfo().allChangesSaved();
    }

    public Class<? extends ViewConfig> cancel() {
        return viewStack.pop();
    }

    @Transactional(qualifier = Main.class)
    public void updateCoverage() {
        int numOfExpenses = 0;
        for (ExpenseCoverage c : coverageMap) {
            if (c.isCovered()) {
                c.getExpense().setReceipt(selected);
                numOfExpenses++;
            } else {
                c.getExpense().setReceipt(null);
            }
            expenseRepo.save(c.getExpense());
        }
        msg.addInfo().expenseCoverageUpdated(numOfExpenses, selected.getDocumentName());
    }

    public boolean isNew(Receipt receipt) {
        return receipt.getId() == null;
    }

    public Receipt getSelected() {
        return selected;
    }

    public void setSelected(Receipt selected) {
        this.selected = selected;
    }

    public List<ExpenseCoverage> getCoverageMap() {
        coverageMap.clear();
        List<Expense> list = expenseRepo.findByReportAndOptionalReceipt(selected.getReport(), selected);
        for (Expense e : list) {
            coverageMap.add(new ExpenseCoverage(e, selected.equals(e.getReceipt())));
        }
        return coverageMap;
    }

    @Typed()
    public static class ExpenseCoverage {

        private Expense expense;

        private boolean covered;

        public ExpenseCoverage(Expense expense, boolean covered) {
            this.expense = expense;
            this.covered = covered;
        }

        public Expense getExpense() {
            return expense;
        }

        public void setExpense(Expense expense) {
            this.expense = expense;
        }

        public boolean isCovered() {
            return covered;
        }

        public void setCovered(boolean covered) {
            this.covered = covered;
        }
    }

    @Named
    @SessionScoped
    public static class UploadController implements Serializable {

        @Inject
        private ReceiptRepository repo;

        @Inject
        private Rules rules;

        private String fileName;

        private byte[] data;

        /*
         * RichFaces methods
         */
        public void uploadListener(FileUploadEvent event) throws Exception {
            this.fileName = event.getUploadedFile().getName();
            this.data = event.getUploadedFile().getData();
        }

        public void generateStored(OutputStream stream, Object object) throws IOException {
            Receipt foundReceipt = repo.findBy((Long) object);
            if (rules.canUserEditReceipts(foundReceipt.getReport())) {
                stream.write(foundReceipt.getDocument());
            }
            stream.close();
        }

        public void generateUploaded(OutputStream stream, Object object) throws IOException {
            stream.write(data);
            stream.close();
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public void clear() {
            data = null;
            fileName = null;
        }
    }

}
