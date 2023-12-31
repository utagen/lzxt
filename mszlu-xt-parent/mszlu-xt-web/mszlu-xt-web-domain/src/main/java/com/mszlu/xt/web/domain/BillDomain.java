package com.mszlu.xt.web.domain;

import com.mszlu.xt.common.login.UserThreadLocal;
import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.common.utils.AESUtils;
import com.mszlu.xt.pojo.Bill;
import com.mszlu.xt.web.domain.repository.BillDomainRepository;
import com.mszlu.xt.web.model.BillModel;
import com.mszlu.xt.web.model.params.BillParam;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BillDomain {
    private BillDomainRepository billDomainRepository;
    private BillParam billParam;

    public BillDomain(BillDomainRepository billDomainRepository, BillParam billParam) {
        this.billDomainRepository = billDomainRepository;
        this.billParam = billParam;
    }

    public CallResult<Object> findAllBillModelList() {
        List<Bill> billList = billDomainRepository.findAll();
        List<BillModel> billModelList = new ArrayList<>();
        for (Bill bill : billList) {
            BillModel billModel = new BillModel();
            BeanUtils.copyProperties(bill, billModel);
            billModelList.add(billModel);
        }
        return CallResult.success(billModelList);
    }

    public CallResult gen() {
        /*
        * 1. 根据id查询海报信息
        * 2. 加密登录的用户id
        * 3. 拼接推广链接
        */
        Long id = this.billParam.getId();
        Bill bill = this.billDomainRepository.findBill(id);
        if (bill == null) {
            return CallResult.fail(-999,"id 不存在");
        }
        String billType = bill.getBillType();
        Long userId = UserThreadLocal.get();
        //AES加密处理
        String encrypt = AESUtils.encrypt(userId.toString());
        //invite.url=http://www.mszlu.com/api/i/u/
        return CallResult.success(billDomainRepository.inviteUrl + billType + "/" + encrypt);
    }
}
