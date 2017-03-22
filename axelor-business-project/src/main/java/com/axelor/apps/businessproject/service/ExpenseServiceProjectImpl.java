/**
 * Axelor Business Solutions
 *
 * Copyright (C) 2017 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.apps.businessproject.service;

import java.util.ArrayList;
import java.util.List;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.service.AccountManagementServiceAccountImpl;
import com.axelor.apps.account.service.AnalyticMoveLineService;
import com.axelor.apps.account.service.app.AppAccountService;
import com.axelor.apps.account.service.move.MoveLineService;
import com.axelor.apps.account.service.move.MoveService;
import com.axelor.apps.hr.db.ExpenseLine;
import com.axelor.apps.hr.db.repo.ExpenseRepository;
import com.axelor.apps.hr.service.config.AccountConfigHRService;
import com.axelor.apps.hr.service.config.HRConfigService;
import com.axelor.apps.hr.service.expense.ExpenseServiceImpl;
import com.axelor.apps.message.service.TemplateMessageService;
import com.axelor.exception.AxelorException;
import com.google.inject.Inject;

public class ExpenseServiceProjectImpl extends ExpenseServiceImpl  {

	@Inject
	public ExpenseServiceProjectImpl(MoveService moveService, ExpenseRepository expenseRepository, MoveLineService moveLineService,
			AccountManagementServiceAccountImpl accountManagementService, AppAccountService appAccountService,
			AccountConfigHRService accountConfigService, AnalyticMoveLineService analyticMoveLineService,
			HRConfigService hrConfigService, TemplateMessageService templateMessageService) {
		
		super(moveService, expenseRepository, moveLineService, accountManagementService, appAccountService, accountConfigService, analyticMoveLineService, hrConfigService, templateMessageService);
	
	}

	@Override
	public List<InvoiceLine> createInvoiceLines(Invoice invoice, List<ExpenseLine> expenseLineList, int priority) throws AxelorException  {

		List<InvoiceLine> invoiceLineList = new ArrayList<InvoiceLine>();
		int count = 0;
		for(ExpenseLine expenseLine : expenseLineList)  {

			invoiceLineList.addAll(this.createInvoiceLine(invoice, expenseLine,priority*100+count));
			count++;
			expenseLine.setInvoiced(true);
			invoiceLineList.get(invoiceLineList.size()-1).setProject(expenseLine.getProjectTask());

		}

		return invoiceLineList;

	}
}
