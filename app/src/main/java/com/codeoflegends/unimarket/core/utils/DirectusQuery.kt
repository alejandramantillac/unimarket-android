package com.codeoflegends.unimarket.core.utils

class DirectusQuery {
    private val fields = mutableListOf<String>("*")
    private val joins = mutableListOf<Join>()
    private val filters = mutableListOf<Filter>()
    private var limit: Int? = null
    private var page: Int? = null
    private val sorts = mutableListOf<Sort>()
    private var searchQuery: String? = null
    private var deepQuery: String? = null
    private val aggregates = mutableListOf<Aggregate>()
    private var groupByField: String? = null
    private var language: String? = null
    private var meta: List<String> = emptyList()

    fun fields(vararg fields: String) = apply {
        this.fields.clear()
        this.fields.addAll(fields)
    }

    fun join(collection: String, vararg fields: String) = apply {
        joins.add(Join(collection, fields.toList()))
    }

    fun filter(field: String, operator: String, value: Any) = apply {
        filters.add(Filter(field, operator, value))
    }

    fun sort(field: String, direction: SortDirection = SortDirection.ASC) = apply {
        sorts.add(Sort(field, direction))
    }

    fun paginate(limit: Int, page: Int? = null) = apply {
        this.limit = limit
        this.page = page
    }

    fun search(query: String) = apply {
        this.searchQuery = query
    }

    fun deep(filter: String) = apply {
        this.deepQuery = filter
    }

    fun aggregate(function: AggregateFunction, field: String) = apply {
        aggregates.add(Aggregate(function, field))
    }

    fun groupBy(field: String) = apply {
        this.groupByField = field
    }

    fun language(lang: String) = apply {
        this.language = lang
    }

    fun includeMeta(vararg metaFields: String) = apply {
        meta = metaFields.toList()
    }

    fun build(): Map<String, String> {
        val params = mutableMapOf<String, String>()

        if (fields.isNotEmpty()) {
            params["fields"] = fields.joinToString(",")
        }

        if (joins.isNotEmpty()) {
            if(params.containsKey("fields")) {
                params["fields"] += "," + joins.joinToString(",") { it.toQueryString() }
            } else {
                params["fields"] = joins.joinToString(",") { it.toQueryString() }
            }
        }

        if (filters.isNotEmpty()) {
            params["filter"] = "{" + filters.joinToString(",") { it.toQueryString() } + "}"
        }

        if (sorts.isNotEmpty()) {
            params["sort"] = sorts.joinToString(",") { it.toQueryString() }
        }

        limit?.let { params["limit"] = it.toString() }
        page?.let { params["page"] = it.toString() }

        searchQuery?.let { params["search"] = it }

        deepQuery?.let { params["deep"] = it }

        if (aggregates.isNotEmpty()) {
            params["aggregate"] = aggregates.joinToString(",") { it.toQueryString() }
        }

        groupByField?.let { params["groupBy"] = it }

        language?.let { params["lang"] = it }

        if (meta.isNotEmpty()) {
            params["meta"] = meta.joinToString(",")
        }

        return params
    }

    // Clases internas
    data class Join(val collection: String, val fields: List<String>) {
        fun toQueryString(): String = if (fields.isEmpty()) {
            "$collection.*"
        } else {
            fields.joinToString(",") { "$collection.$it" }
        }
    }

    data class Filter(val field: String, val operator: String, val value: Any) {
        fun toQueryString(): String {
            val formattedValue = when (value) {
                is String -> "\"$value\""
                else -> value
            }
            return "\"$field\":{\"_$operator\":$formattedValue}"
        }
    }

    data class Sort(val field: String, val direction: SortDirection) {
        fun toQueryString(): String = when (direction) {
            SortDirection.ASC -> field
            SortDirection.DESC -> "-$field"
        }
    }

    data class Aggregate(val function: AggregateFunction, val field: String) {
        fun toQueryString(): String = "${function.name}($field)"
    }

    enum class SortDirection { ASC, DESC }
    enum class AggregateFunction { COUNT, SUM, AVG, MIN, MAX }
}
