"use client";

import { useEffect, useState } from "react";
import Image from "next/image";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Separator } from "@/components/ui/separator";
import { BookOpen, User, Server, Calendar, HandCoins, Star, Dna } from "lucide-react";
import { BookReview } from "@/components/user-page/book-review";
import { RecomendationBook } from "@/components/user-page/book-recommendation";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { SidebarTrigger } from "@/components/ui/sidebar";
import SidebarLayout from "@/components/user-page/sidebar-layout";
import { ShareDrawer } from "@/components/user-page/share-drawer"
import BookmarkButton from "@/common/bookmark-button";
import { Cart } from "@/components/user-page/borrow/cart";
import { useAuthGuard } from "@/common/tokenizer";
import { fetchBookById } from "@/lib/api/books";
import { Book } from "@/types/interfaces";
import { BookReviewForm } from "@/components/user-page/book-review-form";

export default function BookDetailPage({ params }: { params: { id_book: string } }) {
  const { id_book } = params;
  const [bookData, setBookData] = useState<Book | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>("");
  const [visibleReviews, setVisibleReviews] = useState<number>(3);
  const { isLoading } = useAuthGuard();

  useEffect(() => {
    async function loadBook() {
      setLoading(true);
      try {
        const data = await fetchBookById(id_book);
        setBookData(data || null);
        setError("");
      } catch (err) {
        setError(`Failed to load book: ${err}`);
      } finally {
        setLoading(false);
      }
    }

    loadBook();
  }, [id_book]);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p>Loading...</p>
      </div>
    );
  }

  if (loading) {
    return <div className="flex items-center justify-center h-screen">Tunggu bentar, bukunya lagi diambil dari database...</div>;
  }

  if (error) {
    return <div className="flex items-center justify-center h-screen">{error}</div>;
  }

  if (!bookData) {
    return <div className="text-center text-xl font-semibold">Data not found</div>;
  }

  const loadMoreReviews = () => {
    setVisibleReviews((prevVisible) =>
      Math.min(prevVisible + 5, bookData.reviews.length)
    );
  };

  const handleReviewSubmit = async (review: { rating: number; comment: string }) => {
    // Here you would typically send the review to your backend API
    console.log('Submitting review:', review)
    // After successfully submitting the review, you might want to refresh the book data
    // or add the new review to the existing reviews
  }

  const header = (
    <header className="container mx-auto flex h-10 shrink-0 items-center justify-between gap-2 px-4">
      {/* Breadcrumbs Section */}
      <div className="flex items-center gap-2">
        <SidebarTrigger className="-ml-1" />
        <Separator orientation="vertical" className="mr-2 h-4" />
        <Breadcrumb>
          <BreadcrumbList>
            <BreadcrumbItem>
              <BreadcrumbLink href="/collections">Collections</BreadcrumbLink>
            </BreadcrumbItem>
            <BreadcrumbSeparator />
            <BreadcrumbItem>
              <BreadcrumbLink>Book</BreadcrumbLink>
            </BreadcrumbItem>
            <BreadcrumbSeparator />
            <BreadcrumbItem>
              <BreadcrumbLink href={`/collections/book/${bookData.id}`} className="inline-block md:max-w-full max-w-[130px] truncate">
                {bookData.title}
              </BreadcrumbLink>
            </BreadcrumbItem>
          </BreadcrumbList>
        </Breadcrumb>
      </div>
    </header>
  );

  return (
    <SidebarLayout header={header} defaultOpen={false}>
      <div className="container mx-auto px-4 py-8 max-w-4xl">
        <div className="flex flex-col md:flex-row gap-8">
          <div className="md:w-1/3">
            <div className="relative">
              <img
                src={bookData.image}
                alt={bookData.title}
                width={300}
                height={450}
                className="rounded-lg shadow-lg w-full h-auto"
              />
              {/* Overlay for Out of Quota */}
              {!bookData.canBorrow && (
                <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center backdrop-blur-sm">
                  <p className="text-white text-lg font-bold">
                    Maaf (｡•́︿•̀｡) <br /> bukunya lagi banyak <br /> yang baca
                  </p>
                </div>
              )}
              {/* <div className="absolute top-2 right-2 flex gap-2">
                <ShareDrawer
                  title={bookData.title}
                  url={typeof window !== 'undefined' ? window.location.href+"/book/"+bookData.id : ''}
                />
                <BookmarkButton bookId={bookData.id} bookTitle={bookData.title}/>
              </div> */}
            </div>
            <div className="mt-4 flex items-center justify-center">
              <div className="flex items-center">
                {[...Array(5)].map((_, i) => (
                  <Star
                    key={i}
                    className={`w-5 h-5 ${i < Math.floor(bookData.rating)
                      ? "text-yellow-400 fill-yellow-400"
                      : "text-gray-300"
                      }`}
                  />
                ))}
              </div>
              <span className="ml-2 text-sm font-medium">
                {bookData.rating.toFixed(1)}
              </span>
              <span className="ml-1 text-sm text-muted-foreground">
                ({bookData.reviews.length} reviews)
              </span>
            </div>
          </div>
          <div className="md:w-2/3 space-y-6">
            <div>
              <h1 className="text-3xl font-bold mb-2">{bookData.title}</h1>
              <p className="text-xl text-muted-foreground mb-4">
                by {bookData.author}
              </p>
              <div className="flex flex-wrap gap-2 mb-4">
                <Badge variant="secondary">{bookData.category}</Badge>
                <Badge variant="outline">{bookData.language}</Badge>
                {bookData.canBorrow && <Badge variant="default">Available</Badge>}
              </div>
            </div>
            <div className="space-y-2">
              <p className="flex items-center text-sm text-muted-foreground">
                <Dna className="mr-2" size={16} /> ISBN: {bookData.isbn}
              </p>
              <p className="flex items-center text-sm text-muted-foreground">
                <Calendar className="mr-2" size={16} /> Publikasi: {bookData.year}
              </p>
              <p className="flex items-center text-sm text-muted-foreground">
                <HandCoins className="mr-2" size={16} /> Denda: Rp{bookData.lateFee}
              </p>
              <p className="flex items-center text-sm text-muted-foreground">
                <Server className="mr-2" size={16} /> Rak: {bookData.rackNumber}
              </p>
            </div>
            <Separator />
            <div>
              <h2 className="text-xl font-semibold mb-2">Abstract</h2>
              <p className="text-muted-foreground">{bookData.description}</p>
            </div>
            <div className="md:flex items-center justify-between">
              <div className="flex items-center text-sm text-muted-foreground mb-5 md:mb-0">
                <BookOpen className="mr-2" size={16} />
                <span>
                  {bookData.availableCopies} of {bookData.quota} available
                </span>
              </div>
              <div className="flex gap-2 md:gap-4 items-center justify-between">
                <div className="flex gap-2">
                  <ShareDrawer
                    title={bookData.title}
                    url={typeof window !== 'undefined' ? window.location.href : ''}
                  />
                  <BookmarkButton book={bookData} mark="Bookmarked" />
                </div>
                {/* {bookData.canBorrow && (
                  <Button size="lg">Borrow Book</Button>
                )} */}
                {bookData.canBorrow && (
                  <Cart
                    isAdd={true}
                    book={bookData}
                  />

                )}
              </div>
            </div>
          </div>
        </div>
        <Separator className="my-8" />
        <div>
          <h2 className="text-2xl font-semibold mb-4">Write a Review</h2>
          {!isLoading && (
            <BookReviewForm bookId={id_book} onSubmit={handleReviewSubmit} />
          )}
          {isLoading && (
            <p>Please log in to submit a review.</p>
          )}
        </div>
        <Separator className="my-8" />
        <div>
          <h2 className="text-2xl font-semibold mb-4">Reviews</h2>
          <div className="space-y-6">
            {bookData.reviews.slice(0, visibleReviews).map((review) => (
              <BookReview key={review.id} {...review} />
            ))}
          </div>
          {visibleReviews < bookData.reviews.length && (
            <div className="mt-6 text-center">
              <Button onClick={loadMoreReviews} variant="outline">
                Load More Reviews
              </Button>
            </div>
          )}
        </div>
        <Separator className="my-8 hidden md:block" />
        <RecomendationBook />
      </div>
    </SidebarLayout>
  );
}