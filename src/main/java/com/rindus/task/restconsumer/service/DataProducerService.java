package com.rindus.task.restconsumer.service;

import java.util.List;

import com.rindus.task.restconsumer.exception.ConcurentCallException;
import com.rindus.task.restconsumer.model.BaseFields;

public interface DataProducerService {

	List<? extends BaseFields> produceJsonData(List<Integer> idList, BaseFields input) throws ConcurentCallException;
}
