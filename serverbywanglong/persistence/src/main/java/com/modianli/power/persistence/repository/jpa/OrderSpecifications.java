package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.PurchaseOrder;
import com.modianli.power.domain.jpa.PurchaseOrder_;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.domain.jpa.UserAccount_;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author hantsy
 *
 */
public class OrderSpecifications {

    public static Specification<PurchaseOrder> searchOrders(
        LocalDateTime start, //
        LocalDateTime end,//
        PurchaseOrder.Status status, //
        boolean active, //
        Long userId) {
        // final Long userId  = criteria.getUserId();

        return (Root<PurchaseOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (start != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(PurchaseOrder_.placedDate), start));
            }

            if (end != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get(PurchaseOrder_.placedDate), end));
            }

            if (status != null) {
                predicates.add(cb.equal(root.get(PurchaseOrder_.status), status));
            }

            if (userId != null) {
                Join<PurchaseOrder, UserAccount> userJoin = root.join(PurchaseOrder_.user);
                predicates.add(cb.equal(userJoin.get(UserAccount_.id), userId));
            }

            // if (active) {
            predicates.add(cb.equal(root.get(PurchaseOrder_.active), active));
            // }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
