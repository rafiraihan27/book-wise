"use client"

import { useState } from "react"
import Image from "next/image"
import Link from "next/link"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardFooter } from "@/components/ui/card"
import { Checkbox } from "@/components/ui/checkbox"
import { toast } from "sonner"

interface BookmarkItem {
  id: string
  title: string
  author: string
  coverImage: string
  addedDate: string
}

const initialBookmarks: BookmarkItem[] = [
  {
    id: "1",
    title: "The Great Gatsby",
    author: "F. Scott Fitzgerald",
    coverImage: "https://i.pinimg.com/originals/a7/ea/79/a7ea79067b7149982f3856f6d85a22d0.jpg", // Real image
    addedDate: "2023-11-15",
  },
  {
    id: "2",
    title: "The Psychology of Money",
    author: "Morgan Housel",
    coverImage: "https://images-na.ssl-images-amazon.com/images/I/81cpDaCJJCL.jpg", // Real image
    addedDate: "2023-11-10",
  },
  {
    id: "3",
    title: "1984",
    author: "George Orwell",
    coverImage: "https://images-na.ssl-images-amazon.com/images/I/81t2CVWEsUL.jpg", // Real image
    addedDate: "2023-11-05",
  },
]

export default function BookmarksPage() {
  const [bookmarks, setBookmarks] = useState<BookmarkItem[]>(initialBookmarks)
  const [selectedBookmarks, setSelectedBookmarks] = useState<string[]>([])

  const toggleBookmarkSelection = (id: string) => {
    setSelectedBookmarks(prev =>
      prev.includes(id) ? prev.filter(bookmarkId => bookmarkId !== id) : [...prev, id]
    )
  }

  const removeSelectedBookmarks = () => {
    setBookmarks(prev => prev.filter(bookmark => !selectedBookmarks.includes(bookmark.id)))
    setSelectedBookmarks([])
    toast.success("Selected bookmarks removed")
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Bookmarked Books</h1>
        <Button
          variant="destructive"
          onClick={removeSelectedBookmarks}
          disabled={selectedBookmarks.length === 0}
        >
          Remove Selected
        </Button>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {bookmarks.map((bookmark) => (
          <Card key={bookmark.id} className="flex flex-col">
            <CardContent className="flex-grow p-4">
              <div className="flex items-start space-x-4">
                <Checkbox
                  checked={selectedBookmarks.includes(bookmark.id)}
                  onCheckedChange={() => toggleBookmarkSelection(bookmark.id)}
                />
                <Image
                  src={bookmark.coverImage}
                  alt={bookmark.title}
                  width={80}
                  height={120}
                  className="object-cover"
                />
                <div>
                  <h3 className="font-semibold">{bookmark.title}</h3>
                  <p className="text-sm text-muted-foreground">{bookmark.author}</p>
                  <p className="text-xs text-muted-foreground mt-2">
                    Added on {new Date(bookmark.addedDate).toLocaleDateString()}
                  </p>
                </div>
              </div>
            </CardContent>
            <CardFooter className="p-4 pt-0">
              <Link href={`/collections/book/${bookmark.id}`} className="w-full">
                <Button variant="outline" className="w-full">View Book</Button>
              </Link>
            </CardFooter>
          </Card>
        ))}
      </div>
      {bookmarks.length === 0 && (
        <p className="text-center text-muted-foreground">You haven't bookmarked any books yet.</p>
      )}
    </div>
  )
}
