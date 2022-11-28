package com.netwisd.base.common.user.eunm;

/**
 * 数据资源类型枚举
 */
public enum DataModuleTypeEnum {

    EXPENSE_TOTAL_QUERY("expense_total_query", "报销系统综合查询"),
    EXPENSE_INVOICE_QUERY("expense_invoice_query", "报销发票综合查询"),
    PROJECT_TOTAL_QUERY("project_total_query", "项目综合查询"),
    BUDGET_TOTAL_QUERY("budget_total_query", "预算综合查询"),
    CONTRACT_TOTAL_QUERY_CONTRACT("contract_total_query_contract", "合同-合同综合查询"),
    CONTRACT_TOTAL_QUERY_SALES_CONTRACT("contract_total_query_sales_contract", "合同-销售合同综合查询"),
    CONTRACT_TOTAL_QUERY_PURCHASE_CONTRACT("contract_total_query_purchase_contract", "合同-采购合同综合查询"),
    CONTRACT_TOTAL_QUERY_BILL("contract_total_query_bill", "合同-收发票综合查询"),
    CONTRACT_TOTAL_QUERY_OUT_BILL("contract_total_query_out_bill", "合同-开票申请 综合查询"),
    CONTRACT_TOTAL_QUERY_IN_BILL("contract_total_query_in_bill", "合同-发票登记 综合查询"),
    CONTRACT_TOTAL_QUERY_INVOICE_COST("contract_total_query_invoice_cost", "合同-发票综合查询"),
    CONTRACT_TOTAL_QUERY_RECEIVE_AMOUNT("contract_total_query_receive_amount", "合同-收款 综合查询"),
    CONTRACT_TOTAL_QUERY_PAYMENT_AMOUNT("contract_total_query_payment_amount", "合同-付款 综合查询"),
    CONTRACT_TOTAL_QUERY_AMOUNT("contract_total_query_amount", "合同-收付款综合查询"),
    CUSTOMER_SUPPLIER_QUERY("customer_supplier_query", "客商综合查询");


    public String code;
    public String text;

    DataModuleTypeEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public static String getText(String code) {
        for (DataModuleTypeEnum value : DataModuleTypeEnum.values()) {
            if (value.code.equals(code)) {
                return value.text;
            }
        }
        return null;
    }

}
