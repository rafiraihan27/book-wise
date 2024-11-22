"use client"

import { useState } from "react"
import { format } from "date-fns"
import { Bell, Book, Calendar, Check, Trash2 } from 'lucide-react'
import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Checkbox } from "@/components/ui/checkbox"
import { ScrollArea } from "@/components/ui/scroll-area"
import { Separator } from "@/components/ui/separator"
import { toast } from "sonner"

interface Notification {
  id: string
  title: string
  message: string
  type: "info" | "reminder" | "alert"
  date: Date
  read: boolean
}

const initialNotifications: Notification[] = [
  {
    id: "1",
    title: "New Book Available",
    message: "The book 'The Midnight Library' by Matt Haig is now available in our library.",
    type: "info",
    date: new Date(2023, 10, 15, 9, 30),
    read: false,
  },
  {
    id: "2",
    title: "Due Date Reminder",
    message: "The book 'To Kill a Mockingbird' is due tomorrow. Please return or renew it.",
    type: "reminder",
    date: new Date(2023, 10, 14, 14, 0),
    read: true,
  },
  {
    id: "3",
    title: "Late Return Alert",
    message: "Your book 'The Great Gatsby' is overdue. Please return it as soon as possible.",
    type: "alert",
    date: new Date(2023, 10, 13, 11, 45),
    read: false,
  },
  {
    id: "4",
    title: "Book Recommendation",
    message: "Based on your reading history, we think you might enjoy 'The Catcher in the Rye' by J.D. Salinger.",
    type: "info",
    date: new Date(2023, 10, 12, 10, 15),
    read: false,
  },
  {
    id: "5",
    title: "Library Event",
    message: "Join us for a book discussion on 'Pride and Prejudice' this Saturday at 3 PM.",
    type: "info",
    date: new Date(2023, 10, 11, 16, 0),
    read: true,
  },
]

export default function NotificationsPage() {
  const [notifications, setNotifications] = useState<Notification[]>(initialNotifications)
  const [selectedNotifications, setSelectedNotifications] = useState<string[]>([])

  const markAsRead = (id: string) => {
    setNotifications(prevNotifications =>
      prevNotifications.map(notification =>
        notification.id === id ? { ...notification, read: true } : notification
      )
    )
    toast.success("Notification marked as read")
  }

  const deleteNotification = (id: string) => {
    setNotifications(prevNotifications =>
      prevNotifications.filter(notification => notification.id !== id)
    )
    setSelectedNotifications(prevSelected =>
      prevSelected.filter(selectedId => selectedId !== id)
    )
    toast.success("Notification deleted")
  }

  const toggleSelectNotification = (id: string) => {
    setSelectedNotifications(prevSelected =>
      prevSelected.includes(id)
        ? prevSelected.filter(selectedId => selectedId !== id)
        : [...prevSelected, id]
    )
  }

  const markSelectedAsRead = () => {
    setNotifications(prevNotifications =>
      prevNotifications.map(notification =>
        selectedNotifications.includes(notification.id)
          ? { ...notification, read: true }
          : notification
      )
    )
    setSelectedNotifications([])
    toast.success("Selected notifications marked as read")
  }

  const deleteSelectedNotifications = () => {
    setNotifications(prevNotifications =>
      prevNotifications.filter(notification => !selectedNotifications.includes(notification.id))
    )
    setSelectedNotifications([])
    toast.success("Selected notifications deleted")
  }

  const getIconForNotificationType = (type: Notification["type"]) => {
    switch (type) {
      case "info":
        return <Bell className="h-4 w-4 text-blue-500" />
      case "reminder":
        return <Calendar className="h-4 w-4 text-yellow-500" />
      case "alert":
        return <Book className="h-4 w-4 text-red-500" />
      default:
        return <Bell className="h-4 w-4" />
    }
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold">Notifications</h1>
        <p className="text-muted-foreground">
          Manage your notifications and stay updated with your library activities.
        </p>
      </div>
      <Card>
        <CardHeader>
          <CardTitle>Your Notifications</CardTitle>
          <CardDescription>View and manage your recent notifications.</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="flex justify-between mb-4">
            <Button
              variant="outline"
              size="sm"
              onClick={markSelectedAsRead}
              disabled={selectedNotifications.length === 0}
            >
              Mark Selected as Read
            </Button>
            <Button
              variant="destructive"
              size="sm"
              onClick={deleteSelectedNotifications}
              disabled={selectedNotifications.length === 0}
            >
              Delete Selected
            </Button>
          </div>
          <ScrollArea className="h-[400px] pr-4">
            {notifications.map((notification, index) => (
              <div key={notification.id}>
                {index > 0 && <Separator className="my-4" />}
                <div className="flex items-start space-x-4">
                  <Checkbox
                    checked={selectedNotifications.includes(notification.id)}
                    onCheckedChange={() => toggleSelectNotification(notification.id)}
                  />
                  <div className="flex-1">
                    <div className="flex items-center justify-between">
                      <div className="flex items-center space-x-2">
                        {getIconForNotificationType(notification.type)}
                        <h3 className={`font-semibold ${notification.read ? 'text-muted-foreground' : ''}`}>
                          {notification.title}
                        </h3>
                      </div>
                      <span className="text-sm text-muted-foreground">
                        {format(notification.date, "MMM d, yyyy 'at' h:mm a")}
                      </span>
                    </div>
                    <p className={`mt-1 text-sm ${notification.read ? 'text-muted-foreground' : ''}`}>
                      {notification.message}
                    </p>
                    <div className="mt-2 flex justify-end space-x-2">
                      {!notification.read && (
                        <Button
                          variant="ghost"
                          size="sm"
                          onClick={() => markAsRead(notification.id)}
                        >
                          <Check className="h-4 w-4 mr-1" /> Mark as Read
                        </Button>
                      )}
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={() => deleteNotification(notification.id)}
                      >
                        <Trash2 className="h-4 w-4 mr-1" /> Delete
                      </Button>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </ScrollArea>
        </CardContent>
        <CardFooter>
          <p className="text-sm text-muted-foreground">
            You have {notifications.filter(n => !n.read).length} unread notifications.
          </p>
        </CardFooter>
      </Card>
    </div>
  )
}

