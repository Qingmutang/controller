package com.modianli.power.domain.es;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static sun.plugin.javascript.navig.JSType.Document;

/**
 * Created by gao on 17-3-6.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(indexName = "quote_v1", type = "product_price", shards = 1, replicas = 0, refreshInterval = "-1")
public class EnterpriseProductPriceEs implements Serializable {
  @Id
  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Field(type = FieldType.Date,format = DateFormat.year_month_day)
  private Date lastedTime;

  @Field(index = FieldIndex.not_analyzed,type = FieldType.Long)
  private  Long priceCount;

}
