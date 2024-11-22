import Image from "next/image"
import Link from "next/link"
import { Badge } from "@/components/ui/badge"
import { Card, CardContent, CardFooter } from "@/components/ui/card"

interface Book {
  id: number
  title: string
  author: string
  category: string
  year: number
  image: string
  quota: number
}

interface RecommendedBooksProps {
  title?: string
}

const books: Book[] = [
  {
    id: 1,
    title: "The Psychology of Money",
    author: "Morgan Housel",
    category: "finance",
    year: 2020,
    image: "https://images-na.ssl-images-amazon.com/images/I/81cpDaCJJCL.jpg",
    quota: 5,
  },
  {
    id: 2,
    title: "Atomic Habits",
    author: "James Clear",
    category: "self-help",
    year: 2018,
    image: "https://images-na.ssl-images-amazon.com/images/I/91bYsX41DVL.jpg",
    quota: 5,
  },
  {
    id: 3,
    title: "Sapiens",
    author: "Yuval Noah Harari",
    category: "history",
    year: 2015,
    image: "https://images-na.ssl-images-amazon.com/images/I/713jIoMO3UL.jpg",
    quota: 0,
  },
  {
    id: 4,
    title: "The Lean Startup",
    author: "Eric Ries",
    category: "business",
    year: 2011,
    image: "https://images-na.ssl-images-amazon.com/images/I/81-QB7nDh4L.jpg",
    quota: 5,
  }
];

export function RecomendationBook({ title = "Recommended Books" }: RecommendedBooksProps) {
  return (
    <div className="w-full mt-10">
      <h2 className="text-2xl font-bold mb-4">{title}</h2>
      <div className="overflow-x-auto whitespace-nowrap">
        {books.map((book) => (
          <Card key={book.id} className="inline-block overflow-hidden w-[200px] mx-2">
            <div className="relative aspect-[3/4]">
              <Image
                src={book.image}
                alt={`${book.title} Cover`}
                layout="fill"
                className="object-cover"
                priority
              />
              {book.quota === 0 && (
                <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center">
                  <p className="text-white text-sm font-bold text-center px-2">
                    Currently Unavailable
                  </p>
                </div>
              )}
            </div>
            <CardContent className="p-4">
              <Link href={`/collections/book/${book.id}`} className="hover:underline">
                <h3 className="font-bold truncate">{book.title}</h3>
              </Link>
              <p className="text-sm text-muted-foreground">{book.author}</p>
            </CardContent>
            <CardFooter className="p-4 pt-0 flex justify-between">
              <Badge variant="secondary">{book.category}</Badge>
              <Badge variant="outline">{book.year}</Badge>
            </CardFooter>
          </Card>
        ))}
      </div>
    </div>
  )
}
