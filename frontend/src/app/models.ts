export interface Summary {
    gid: string
    title: string
}

export interface Comment {
    gid: string
    comment: string
    timestamp: string
}

export interface Gif {
    gid: string
    title: string
    username: string
    url: string
    comments: Comment[]
}

export interface SummarySlice {
    loadedOn: number
    summaries: Summary[]
}