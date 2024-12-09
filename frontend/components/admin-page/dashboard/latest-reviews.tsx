import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Card, CardContent } from "@/components/ui/card"

export function LatestReviews() {
  return (
    <div className="space-y-8">
      <Card>
        <CardContent className="pt-6">
          <div className="flex items-center">
            <Avatar className="h-9 w-9">
              <AvatarImage src="/avatars/01.png" alt="Avatar" />
              <AvatarFallback>JD</AvatarFallback>
            </Avatar>
            <div className="ml-4 space-y-1">
              <p className="text-sm font-medium leading-none">John Doe</p>
              <p className="text-sm text-muted-foreground">
                Reviewed "The Psychology of Money"
              </p>
            </div>
            <div className="ml-auto font-medium">⭐⭐⭐⭐⭐</div>
          </div>
          <p className="mt-4 text-sm text-muted-foreground">
            "An insightful book that provides a great introduction to the psychological aspects of financial decision-making. Highly recommended for anyone interested in personal finance."
          </p>
        </CardContent>
      </Card>
      <Card>
        <CardContent className="pt-6">
          <div className="flex items-center">
            <Avatar className="h-9 w-9">
              <AvatarImage src="/avatars/02.png" alt="Avatar" />
              <AvatarFallback>JS</AvatarFallback>
            </Avatar>
            <div className="ml-4 space-y-1">
              <p className="text-sm font-medium leading-none">Jane Smith</p>
              <p className="text-sm text-muted-foreground">
                Reviewed "To Kill a Mockingbird"
              </p>
            </div>
            <div className="ml-auto font-medium">⭐⭐⭐⭐</div>
          </div>
          <p className="mt-4 text-sm text-muted-foreground">
            "A classic that still resonates today. Harper Lee's storytelling is powerful and thought-provoking. A must-read for everyone."
          </p>
        </CardContent>
      </Card>
      <Card>
        <CardContent className="pt-6">
          <div className="flex items-center">
            <Avatar className="h-9 w-9">
              <AvatarImage src="/avatars/03.png" alt="Avatar" />
              <AvatarFallback>RJ</AvatarFallback>
            </Avatar>
            <div className="ml-4 space-y-1">
              <p className="text-sm font-medium leading-none">Robert Johnson</p>
              <p className="text-sm text-muted-foreground">
                Reviewed "1984"
              </p>
            </div>
            <div className="ml-auto font-medium">⭐⭐⭐⭐⭐</div>
          </div>
          <p className="mt-4 text-sm text-muted-foreground">
            "Orwell's dystopian vision is chillingly relevant. The book's exploration of surveillance and control is more pertinent than ever in our digital age."
          </p>
        </CardContent>
      </Card>
    </div>
  )
}

