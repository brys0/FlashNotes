package com.brys.flashnotes.api.revised

interface GenericItem: GenericAny {
    var created_by: String
}

interface GenericAny {
    var id: String
}