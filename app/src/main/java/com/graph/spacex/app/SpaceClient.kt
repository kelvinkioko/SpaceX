package com.graph.spacex.app

import com.apollographql.apollo3.ApolloClient

object SpaceClient {
    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .build()
}